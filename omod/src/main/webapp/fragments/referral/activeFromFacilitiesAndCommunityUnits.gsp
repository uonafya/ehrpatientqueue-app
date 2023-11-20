<script type="text/javascript">
jq(document).ready(function () {
var activeReferralsTbl = jq('#activeReferralsTbl').DataTable();
var patientShrDetailsDialog = emr.setupConfirmationDialog({
      dialogOpts: {
          overlayClose: false,
          close: true
      },
      selector: '#view-patient-shr-details-dialog',
      actions: {
          confirm: function () {
              jq.getJSON('${ui.actionLink("patientqueueapp", "referral/referralHistory", "addPatientLocallyAndRedirect")}',
                  {
                      activeId: jq("#activeId").val()
                  }
              ).success(function (data) {
                 //redirect the page to initial patient queue app
              });
          },
          cancel: function () {
              patientShrDetailsDialog.close();
              location.reload();
          }
      }
  });
  jq('#activeReferralsTbl tbody').on( 'click', 'tr', function (e) {
          e.preventDefault();
          var values = activeReferralsTbl.row(this).data();
          console.log(values);
          jQuery('#activeId').val(values[0]);
          getClientShrDetails();
          patientShrDetailsDialog.show();

    });
});
function getClientShrDetails(){
      jq.getJSON('${ ui.actionLink("patientqueueapp", "referral/referralHistory", "getPatientShrHistory") }', {
                      activeId: jq("#activeId").val()
      }).success(function (data) {
        if(data) {
              if(data.cancerReferral.length > 0) {
                  for (var i = 0; i < data.cancerReferral.length; i++) {
                    console.log(data);
                  }
              }
              else {
                console.log("Data ==>"+data);
              }
        }

      });
}
</script>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">Active Referrals</div>
    <div class="ke-panel-content" style="background-color: #F3F9FF;">
        <table id="activeReferralsTbl" width="100%">
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
              <% activeReferralList.each {%>
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