<%
    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
    ui.includeCss("ehrconfigs", "referenceapplication.css")
%>
<script type="text/javascript">
jq(document).ready(function () {
     var jq = jQuery;
    jq("#diagnosis").on("focus.autocomplete", function () {
            jq(this).autocomplete({
                source: function(request, response) {
                        jq.getJSON('${ ui.actionLink("patientdashboardapp", "clinicalNotes", "getDiagnosis") }', {
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
        jq("#confirmBtn").on('click', function () {
                    confirmDeath();
                    ui.navigate('patientqueueapp', 'deaths/deathReporting', {section: 'deceased'});
        });
        jq("#referToMorgue").on('click', function () {
                    referToMorgue();
        });
    });
    function confirmDeath() {
        jq.getJSON('${ ui.actionLink("patientqueueapp", "patientQueue", "certifyDeath") }', {
            deathDate:jq("#summaryDeathDate-field").val(),
            diagnosis: jq("#diagnosis").val(),
            patientId: jq("#patientId").val(),
            deathNotes: jq("#deathNotes").val(),
        }).success(function(data) {
            jq().toastmessage('showSuccessToast', "Death Certified successfully");
        });
    }

    function referToMorgue() {
            alert("Under Development");
        }
</script>
<div class="ke-page-content">
    <form class="simple-form-ui">
        <table align="center" style="width : 75%" border="0" cellpadding="0" cellspacing="0">
        <input type="hidden" id="patientId" name="patientId" value="${patientId}" />
            <tr>
                <td style="width:25%" valign="top">&nbsp;</td>
                <td>
                    <h2>Date and time of death</h2>
                    <p class="input-position-class">
                            ${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'deathDate', id: 'summaryDeathDate', label: '', useTime: true, defaultToday: true, class: ['newdtp']])}
                    </p>
                    <h2>Cause of death <span class="important">*</span></h2>
                    <p class="input-position-class">
                        <input type="text" id="diagnosis" name="diagnosis" placeholder="Select Diagnosis" size="60" />
                    </p>
                    <h2>Notes</h2>
                    <textarea id="deathNotes" name="deathNotes" cols="50" rows="5"></textarea>
                    <div class="onerow" style="margin-top: 60px">

                        <a class="button confirm" id="confirmBtn"
                           style="float:right; display:inline-block; margin-left: 5px;">
                            <span>CONFIRM</span>
                        </a>

                        <a class="button cancel" onclick="window.location.href = window.location.href"
                           style="float:right; display:inline-block;"/>
                            <span>RESET</span>
                        </a>
                    </div>
                </td>
                <td style="width:15%" valign="top">
                    <div class="ke-panelbar" style="text-align: right">
                        <button type="button" id="referToMorgue">
                            <img src="${ ui.resourceLink("patientqueueapp", "images/morgue.png") }" /> Refer to Morgue
                        </button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>