<style type="text/css">

.button-large{
    padding:24px 40px;
    font-size:17px;
}

#credit-card-information{
    background:#f8f8f8;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
}
</style>


<div id="donate-container" class="mid-container">

    <h1>Donate to Zeus</h1>

    <p>If you like the project and the direction it is going,
    please help support by making a donation. <br/>Thank you!</p>

    <div id="purchase-form" style="">

        <form id="donation-form" name="donation-form" class="pure-form">
            <fieldset>
                <input type="hidden" name="stripe-token" id="stripe-token" value="" />

                <label for="amount">Amount</label>
                <input type="text" name="amount" value="" class="base-form" style="width:71px;"/>

                <label for="email">Email</label>
                <input type="text" name="email" placeholder="Email"  class="base-form"/>
            </fieldset>
        </form>


        <h3>Credit Card Information</h3>
        <div id="credit-card-information" style="margin-top:20px;padding:20px; border:solid 1px #ddd"></div>

        <div id="donation-processing" style="display:none;margin-top:20px;">Processing, please wait...</div>

        <br class="clear"/>

        <a href="javascript:" class="button beauty-light float-right" id="submit-donation-button">Donate</a>

        <br class="clear"/>

    </div>

</div>

<script src="https://js.stripe.com/v3/"></script>
<script type="text/javascript">

    var stripe = {},
        elements = {},
        card = {};

    var donateContainer = document.getElementById("donate-container")

    var donationForm = document.getElementById("donation-form")
        creditCardInfo = document.getElementById("credit-card-information")
        processingDonate = document.getElementById("donation-processing"),
        donateButton = document.getElementById("submit-donation-button"),
        stripeToken = document.getElementById("stripe-token");

    stripe = Stripe("pk_live_D2JuVMbBm0B5kVJz949oR6OB");
    elements = stripe.elements()

    card = elements.create('card', {
        base : {
            fontSize: '29px',
            lineHeight: '48px'
        }
    })
    card.mount('#credit-card-information')
    card.addEventListener('change', function(event) {
        var displayError = document.getElementById('card-errors');
        if (event.error) {
            processingDonate.innerHTML = event.error.message
            processingDonate.style.display = "block"
        } else {
            processingDonate.style.display = "none"
            processingDonate.innerHTML = "Processing donation... thank you"
        }
    });

    donateButton.addEventListener("click", function(event){
        event.preventDefault()
        processingDonate.style.display = "block"
        stripe.createToken(card).then(function(result) {
            //console.log("result", result)
            stripeToken.value = result.token.id
            var uri = "${pageContext.request.contextPath}/donate"
            web.publish(uri, donationForm).then(thankYou).catch(error)
        });
    })


    function thankYou(request){
        var response = JSON.parse(request.responseText)
        window.location = response.receipt_url
    }

</script>