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
                      nupiNumber: jq("#nupiNumber").val(),
                      firstName: jq("#firstName").val(),
                      middleName: jq("#middleName").val(),
                      familyName: jq("#familyName").val(),
                      sex: jq("#sex").val(),
                      dob: jq("#dob").val()
                  }
              ).success(function (data) {
                 if(data) {
                    ui.navigate('initialpatientqueueapp', 'patientCategory', {patientId: data.id});
                 }
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
          jQuery('#nupiNumber').val(values[6]);
          jQuery('#firstName').val(values[1]);
          jQuery('#middleName').val(values[2]);
          jQuery('#familyName').val(values[3]);
          jQuery('#sex').val(values[4]);
          jQuery('#dob').val(values[5]);
          jq("#pDetails").text(values[6]);
          getClientShrDetails();
          patientShrDetailsDialog.show();

    });
});
function getClientShrDetails(){
      jq.getJSON('${ ui.actionLink("patientqueueapp", "referral/referralHistory", "getPatientShrHistory") }', {
                      activeId: jq("#activeId").val()
      }).success(function (data) {
        if(data) {
             jq("#dateOfReferral").text(data.referralDate);
             jq("#category").text(data.category);
             jq("#reason").text(data.reasonCode);
             jq("#notes").text(data.clinicalNote);
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