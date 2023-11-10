<script>
    var jq = jQuery;
    jq(function () {
      jq("#referralLocation").on("focus.autocomplete", function () {
                  jq(this).autocomplete({
                      source: function(request, response) {
                              jq.getJSON('${ ui.actionLink("patientdashboardapp", "referral/actualReferral", "getEncounterLocation") }', {
                                q: request.term
                              }).success(function(data) {
                                var results = [];
                                for (var i in data) {
                                    var result = {
                                      label: data[i].name,
                                      value: data[i].uuid
                                    };
                                    results.push(result);
                                }
                                response(results);
                              });
                            },
                            minLength: 3,
                            select: function(event, ui) {
                              event.preventDefault();
                              jq(this).val(ui.item.label);
                            }
                  });
              });
              jq("#confirmReferralBtn").on('click', function () {
                  confirmReferral();
                  ui.navigate('patientqueueapp', 'referral/referredPatients');
              });
    });
    function confirmReferral() {
      alert("Ready to go");
    }
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Refer Patient from ths facility</div>
    <div class="ke-panel-content">
        <h2>Facility to refer patient to <span class="important">*</span></h2>
        <p class="input-position-class">
            <input type="text" id="referralLocation" name="referralLocation" placeholder="Select facility" size="60" />
        </p>
        <h2>Referral Reasons <span class="important">*</span></h2>
            <select id="referralReason" name="referralReason">
              <% referralReason.each {%>
                <option>${it.displayString}</option>
              <%}%>
            </select>
        <h2>Clinical Notes</h2>
            <textarea id="deathNotes" name="deathNotes" cols="50" rows="5"></textarea>
        <div class="onerow" style="margin-top: 60px">

            <a class="button confirm" id="confirmReferralBtn"
               style="float:right; display:inline-block; margin-left: 5px;">
                <span>Refer</span>
            </a>

            <a class="button cancel" onclick="window.location.href = window.location.href"
               style="float:right; display:inline-block;"/>
                <span>Reset</span>
            </a>
        </div>
    </div>
</div>

