<script type="text/javascript">
jq(document).ready(function () {
  var completedReferralsTbl = jq('#completedReferralsTbl').DataTable();
  var servedPatientShrDetailsDialog = emr.setupConfirmationDialog({
        dialogOpts: {
            overlayClose: false,
            close: true
        },
        selector: '#view-completed-patient-shr-details-dialog',
        actions: {
            confirm: function () {
                jq.getJSON('${ui.actionLink("kenyaemrIL", "referralsDataExchange", "updateShrReferral")}',
                    {
                        patientId: jq("#servedActiveId").val()
                    }
                ).success(function (data) {
                   if(data) {
                      location.reload();
                   }
                });
            },
            cancel: function () {
                servedPatientShrDetailsDialog.close();
                location.reload();
            }
        }
    });

  jq('#completedReferralsTbl tbody').on( 'click', 'tr', function (e) {
    e.preventDefault();
    var servedPatientValues = completedReferralsTbl.row(this).data();
    jq("#servedPDetails").text(servedPatientValues[6]);
    jq("#servedActiveId").text(servedPatientValues[0]);
    getServedClientShrDetails();
    servedPatientShrDetailsDialog.show();
    });
});
function getServedClientShrDetails(){
      jq.getJSON('${ ui.actionLink("patientqueueapp", "referral/referralHistory", "getPatientShrHistory") }', {
                      servedActiveId: jq("#servedActiveId").val()
      }).success(function (data) {
        console.log(data);
        if(data) {
             jq("#servedDateOfReferral").text(data.referralDate);
             jq("#servedCategory").text(data.category);
             jq("#servedReason").text(data.reasonCode);
             jq("#servedNotes").text(data.clinicalNote);
        }

      });
}
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Serviced Referrals</div>
    <div class="ke-panel-content" style="background-color: #F3F9FF;">
            <table id="completedReferralsTbl" width="100%">
                <thead>
                  <tr>
                      <th style="display:none;">ID</th>
                      <th>First Name</th>
                      <th>Middle Name</th>
                      <th>Family Name</th>
                      <th>Sex</th>
                      <th>DOB</th>
                      <th>Identifier</th>
                      <th>Referral Date</th>
                      <th>Referred From</th>
                  </tr>
                </thead>
                <tbody>
                  <% completedReferralList.each {%>
                      <tr>
                          <td style="display:none;">${it.id}</td>
                          <td>${it.givenName}</td>
                          <td>${it.middleName}</td>
                          <td>${it.familyName}</td>
                          <td>${it.gender}</td>
                          <td>${it.birthdate}</td>
                          <td>${it.nupi}</td>
                          <td>${it.dateReferred}</td>
                          <td>${it.referredFrom}</td>
                      </tr>
                  <%}%>
                </tbody>
            </table>
        </div>
</div>