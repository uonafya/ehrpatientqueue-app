<script type="text/javascript">
jq(document).ready(function () {
  var completedReferralsTbl = jq('#completedReferralsTbl').DataTable();
});
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