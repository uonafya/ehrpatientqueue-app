<script>
    var jq = jQuery;
    jq(function () {
        jq("#referralFacilityLocation").on("focus.autocomplete", function () {
                  jq(this).autocomplete({
                      source: function(request, response) {
                              jq.getJSON('${ ui.actionLink("patientqueueapp", "referral/actualReferral", "getEncounterLocation") }', {
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
                  jq().toastmessage('showSuccessToast', 'Patient referral created successfully!');
              });
              jq("#referralType").on('change', function() {
                var response = jq(this).val();
                if(response === 'Community') {
                    jq("#communityDiv").show();
                    jq("#facilityDiv").hide();
                }
                if(response === 'Facility') {
                  jq("#communityDiv").hide();
                  jq("#facilityDiv").show();
                }
              });
    });
    function confirmReferral() {
      jq.getJSON('${ ui.actionLink("patientqueueapp", "referral/actualReferral", "savePatientReferral") }', {
            referralType: jq("#referralType").val(),
            referralCommunityUnit: jq("#referralCommunityUnit").val(),
            referralCommunityName: jq("#referralCommunityName").val(),
            referralFacilityLocation: jq("#referralFacilityLocation").val(),
            referralReason: jq("#referralReason").val(),
            referralNotes: jq("#referralNotes").val()
        }).success(function (data) {
        });
    }
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Refer Patient from ths facility</div>
    <div class="ke-panel-content">
        <h2>Referral Type</h2>
        <select id="referralType" name="referralType">
          <option></option>
          <% referralType.each {%>
            <option value="${it.conceptId}">${it.displayString}</option>
          <%}%>
        </select>
        <div id="communityDiv" style="display:none">
          <h2>Community unit to refer patient to</span></h2>
          <p class="input-position-class">
              <input type="text" id="referralCommunityUnit" name="referralCommunityUnit" placeholder="Enter Community unit code" size="60" />
          </p>
          <h2>Community name to refer patient to</span></h2>
          <p class="input-position-class">
              <input type="text" id="referralCommunityName" name="referralCommunityName" placeholder="Enter Community unit name" size="60" />
          </p>
        </div>
        <div id="facilityDiv" style="display:none">
          <h2>Facility to refer patient to</span></h2>
          <p class="input-position-class">
              <input type="text" id="referralFacilityLocation" name="referralFacilityLocation" placeholder="Select facility" size="60" />
          </p>
        </div>
        <h2>Referral Reasons</span></h2>
            <select id="referralReason" name="referralReason">
              <option value=""></option>
              <% referralReason.each {%>
                <option value="${it.conceptId}">${it.displayString}</option>
              <%}%>
            </select>
        <h2>Clinical Notes</h2>
        <textarea id="referralNotes" name="referralNotes" cols="50" rows="5"></textarea>
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

