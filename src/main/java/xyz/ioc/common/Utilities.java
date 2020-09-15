package xyz.ioc.common;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.exif.ExifIFD0Directory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;
import xyz.ioc.model.MusicFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	
	@Autowired
	private ApplicationContext applicationContext;


	public int generateRandomNumber(int max){
		Random r = new Random();
		return r.nextInt(max);
	}

	public String hash(String password){
		MessageDigest md = null;
        StringBuffer passwordHashed = new StringBuffer();
        
		try {
			md = MessageDigest.getInstance("SHA-256");
		    md.update(password.getBytes());
	 
	        byte byteData[] = md.digest();
	 
	        for (int i = 0; i < byteData.length; i++) {
	        	passwordHashed.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    return passwordHashed.toString();
	}

	
	public boolean containsSpecialCharacters(String str) {
		Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		boolean b = m.find();

		if(b){
			return true;
		}
		
		return false;
	}

	
	public String writeMp3(CommonsMultipartFile file, String dir){
		
		String[] contentTypes = new String[]{ "audio/mp3", "audio/mpeg" };
		List<String> list = Arrays.asList(contentTypes);

		if(!correctMimeType(list, file)){
			return "";
		}

        String appPath = getApplicationPath();
        String musicDir = appPath + dir;

        String fileName = generateFileName(file);

		boolean dirCreated = new File(musicDir).mkdirs();

		write(file, musicDir, fileName);

		String uri = dir + fileName;

        return uri;
	}


    public String writeVideo(CommonsMultipartFile file, String dir){

		String[] contentTypes = new String[]{"video/mp4"};
		List<String> list = Arrays.asList(contentTypes);

		if(!correctMimeType(list, file)){
			System.out.println("not eq video");
			return "";
		}

        String appPath = getApplicationPath();
        String videoDir = appPath + dir;

        String fileName = generateFileName(file);

        boolean dirCreated = new File(videoDir).mkdirs();

        write(file, videoDir, fileName);

        String uri = dir + fileName;

        return uri;

    }


	//TODO:break & fix
	public String write(CommonsMultipartFile file, String dir){

		String[] contentTypes = new String[]{"image/png", "image/jpeg", "image/jpg", "image/gif"};
		List<String> list = Arrays.asList(contentTypes);

		if(!correctMimeType(list, file)){
			return "";
		}

        String appPath = getApplicationPath();
        String imageDir = appPath + dir;

        String fileName = generateFileName(file);

        boolean dirCreated = new File(imageDir).mkdirs();

		write(file, imageDir, fileName);
		String uri = dir + fileName;

		return uri;

	}


	public void write(CommonsMultipartFile file, String fileDirectory, String fileName) {

		File tempFile = new File(fileDirectory + fileName);
		Path filepath = tempFile.toPath();

	    try (OutputStream os = Files.newOutputStream(filepath)) {
	        os.write(file.getBytes());
	    }catch(Exception e){
	    	System.out.println("\n\ncu : unable to write file");
	    }

	}


	public void writeImage(CommonsMultipartFile file, String imageDirectory, String fileName) {

		File tempFile = new File(imageDirectory + fileName);
		Path filepath = tempFile.toPath();

		com.drew.metadata.Metadata metadata;

		try {

			OutputStream os = Files.newOutputStream(filepath);
			os.write(file.getBytes());

			metadata = ImageMetadataReader.readMetadata(tempFile);

			if(metadata.containsDirectoryOfType(ExifIFD0Directory.class)) {

				System.out.println(" >>> contains exif ");

				ExifIFD0Directory exifIFD0 = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

				if(exifIFD0.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {

					int orientation = exifIFD0.getInt(ExifIFD0Directory.TAG_ORIENTATION);

					Rotation rotation = getImageRotation(orientation);

					BufferedImage image = ImageIO.read(tempFile);
					Scalr scalr = new Scalr();
					BufferedImage rotated = scalr.rotate(image, rotation);

					File output = new File(imageDirectory + fileName);

					System.out.println(tempFile.toPath());
					System.out.println(output.toPath());

					ImageIO.write(rotated, getFileExtension(fileName), output);

				}
			}

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("\n\ncu : unable to write file");
		}

	}

	private org.imgscalr.Scalr.Rotation getImageRotation(int orientation) {
		Rotation rotation = null;
		switch (orientation) {
			case 1: // [Exif IFD0] Orientation - Top, left side (Horizontal / normal)
				rotation = null;
				break;
			case 6: // [Exif IFD0] Orientation - Right side, top (Rotate 90 CW)
				rotation = Rotation.CW_90;
				break;
			case 3: // [Exif IFD0] Orientation - Bottom, right side (Rotate 180)
				rotation = Rotation.CW_180;
				break;
			case 8: // [Exif IFD0] Orientation - Left side, bottom (Rotate 270 CW)
				rotation = Rotation.CW_270;
				break;
		}
		return rotation;
	}



	public boolean correctMimeType(List<String> mimetypes, CommonsMultipartFile file){
		try {

			String tmpname = generateFileName(file);
			String appPath = getApplicationPath();
			String pathname = appPath + "/tmp/";

			boolean dirCreated = new File(pathname).mkdirs();

			Path tmpPath = new File(pathname + tmpname).toPath();

			write(file, pathname, tmpname);

			String mimetype = Files.probeContentType(tmpPath);

			File tmpFile = new File(pathname + tmpname);
			URLConnection connection = tmpFile.toURL().openConnection();

			if(mimetype == null || mimetype == "content/unknown" )mimetype = connection.getContentType();
			if(mimetype == null || mimetype == "content/unknown" )mimetype = file.getContentType();
			System.out.println("mimetype : " + mimetype + " : c: " +  file.getContentType() + " p : " + tmpPath);

			if (!mimetypes.contains(mimetype)) {
				System.out.println("cu : not in correct mime types");
				return false;
			}

			deleteUploadedFile("/tmp/" + tmpname);

			return true;

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}


	public String generateFileName(CommonsMultipartFile file){
	    
	    FileItem fileItem = file.getFileItem();
	    String originalName = fileItem.getName();
	    
	    String fileName = generateRandomString(9);
	    String extension = getFileExtension(originalName);

	   	return fileName + "." +  extension;
	}




	private String getFileExtension(final String path) {
	    String result = null;
	    if (path != null) {
	        result = "";
	        if (path.lastIndexOf('.') != -1) {
	            result = path.substring(path.lastIndexOf('.'));
	            if (result.startsWith(".")) {
	                result = result.substring(1);
	            }
	        }
	    }
	    return result;
	}



	public String generateRandomString(int n) {
        String CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
        StringBuilder uuid = new StringBuilder();
        Random rnd = new Random();
        while (uuid.length() < n) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            uuid.append(CHARS.charAt(index));
        }
        return uuid.toString();
    }


	public boolean validEmail(String str){
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(str);
	}
		

	public String getApplicationPath(){
		try {
			Resource propResource = applicationContext.getResource(".");
			return propResource.getURI().getPath().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}


	public boolean deleteUploadedFile(String fileUri){
		if(fileUri != "") {

			String applicationPath = getApplicationPath();
			String filePath = applicationPath + fileUri;
			System.out.println("file path : " + filePath);

			File file = new File(filePath);
			file.delete();

			return true;
		}
		else{
			return false;
		}
	}


	public long getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        long date = getDateFormatted(cal);
		return date;
	}
	
	public long getPreviousDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -day);
        long date = getDateFormatted(cal);
        return date;
	}

	public long getPrevious7Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		long date = getDateFormatted(cal);
		return date;
	}

	public long getPrevious14Days(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -14);
		long date = getDateFormatted(cal);
		return date;
	}

	public long getPreviousMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -31);
		long date = getDateFormatted(cal);
		return date;
	}

	private long getDateFormatted(Calendar cal) {
        DateFormat df = new SimpleDateFormat(Constants.DATE_SEARCH_FORMAT);
        String dateStr = df.format(cal.getTime());
        long date = Long.parseLong(dateStr);
        return date;
	}

	public Date getDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		Date date = cal.getTime();
		return date;
	}


	public String getGraphDate(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -day);
		DateFormat df = new SimpleDateFormat(Constants.DATE_GRAPH_FORMAT);
		String dateStr = df.format(cal.getTime());
		return dateStr;
	}
}