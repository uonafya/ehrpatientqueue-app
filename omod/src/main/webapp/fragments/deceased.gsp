<%
    ui.includeCss("ehrconfigs", "referenceapplication.css")
    ui.includeJavascript("patientdashboardapp", "jq.print.js")
%>
<script type="text/javascript">
 var jq = jQuery;
     jq(function () {
     var table = jQuery("#deceasedTbl").DataTable({
               dom: 'Bfrtip',
               buttons: ['copy', 'csv', 'excel',
                   {
                       extend: 'print',
                       messageTop: 'Deceased Cases',
                       customize: function (win) {
                           jq(win.document.body)
                               .prepend(`${ ui.includeFragment("patientdashboardapp", "printHeader") }`);
                       },
                       repeatingHead: {
                           logo: '${ui.resourceLink('ehrinventoryapp', 'images/kenya_logo.bmp')}',
                           logoPosition: 'center',
                           logoStyle: ''
                       },
                       title: ''
                   }
               ]
           });

           jQuery('#deceasedTbl tbody').on( 'click', 'tr', function () {
               console.log( table.row( this ).data() );
               jq("#printSection").print({
               				globalStyles: 	false,
               				mediaPrint: 	false,
               				stylesheet: 	'${ui.resourceLink("patientdashboardapp", "styles/printout.css")}',
               				iframe: 		false,
               				width: 			600,
               				height:			700
              });
           } );

     });

</script>
<style type="text/css">
#person-detail{
      display: none;
    }
</style>
<div class="ke-page-content">
    <div class="ke-panel-frame">
         <div class="ke-panel-heading">Confirmed Deceased Cases</div>
             <div class="ke-panel-content">
                <table border="0" cellpadding="0" cellspacing="0" id="deceasedTbl" width="100%">
                    <thead>
                        <tr>
                            <th style="display: none;">Patient Id</th>
                            <th>Name</th>
                            <th>Date and Time of Death</th>
                            <th>Cause of Death</th>
                            <th>Entry date and time</th>
                            <th>Notes</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (allDeceasedAndConfirmedCases.empty) { %>
                            <tr>
                                <td colspan="7">
                                    No records found for specified period
                                </td>
                            </tr>
                        <% } %>
                        <% if (allDeceasedAndConfirmedCases) { %>
                            <% allDeceasedAndConfirmedCases.each {%>
                                <tr>
                                    <td style="display:none;">${it.patientId}</td>
                                    <td>${it.name}</td>
                                    <td>${it.dOfDeath}</td>
                                    <td>${it.causeOfDeath}</td>
                                    <td>${it.entryTime}</td>
                                    <td>${it.notes}</td>
                                    <td>${it.status}</td>
                                </tr>
                            <%}%>
                        <%}%>
                    </tbody>
                </table>
            </div>
         </div>
    </div>
</div>
<div class="col16 dashboard" style="min-width: 78%;">
		<div id="printSection">
		    <div id="person-detail">
		        <center>
              <img width="100" height="100" align="center" title="Integrated KenyaEMR" alt="Integrated KenyaEMR" src="${ui.resourceLink('ehrinventoryapp', 'images/kenya_logo.bmp')}">
              <h2>${userLocation}(${mfl}) </h2>
            </center>
            <h3>SUMMARY INFORMATION</h3>
		    </div>
		</div>
</div>
