package xyz.ioc.model;

public class AccountBlock {


    public static class Builder {

        long id;

        long personId;

        long blockerId;

        long dateBlocked;

        public Builder byBlocker(long blockerId){
            this.blockerId = blockerId;
            return this;
        }

        public Builder forPerson(long personId){
            this.personId = personId;
            return this;
        }

        public Builder atDateBlocked(long dateBlocked){
            this.dateBlocked = dateBlocked;
            return this;
        }

        public AccountBlock build(){
            AccountBlock blok = new AccountBlock();
            blok.personId = this.personId;
            blok.blockerId = this.blockerId;
            blok.dateBlocked = this.dateBlocked;
            return blok;
        }
    }



    long id;

    long personId;

    long blockerId;

    long dateBlocked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getBlockerId() {
        return blockerId;
    }

    public void setBlockerId(long blockerId) {
        this.blockerId = blockerId;
    }

    public long getDateBlocked() {
        return dateBlocked;
    }

    public void setDateBlocked(long dateBlocked) {
        this.dateBlocked = dateBlocked;
    }
}
