<%
	ui.decorateWith("appui", "standardEmrPage", [ layout: "sidebar" ])
	ui.includeJavascript("financials", "jquery.dataTables.min.js")
  ui.includeJavascript("ehrconfigs", "emr.js")
  ui.includeJavascript("ehrconfigs", "jquery.simplemodal.1.4.4.min.js")
  ui.includeCss("ehrconfigs", "referenceapplication.css")
  ui.includeCss("financials", "jquery.dataTables.min.css")

	def menuItems = [

              [ label: "Back to home",
                iconProvider: "kenyaui",
                icon: "buttons/back.png",
                href: ui.pageLink("patientqueueapp", "referral/patientReferral")
              ]
      ]
%>
<script type="text/javascript">
    jq(document).ready(function () {
        jq("#ftabs").tabs();
       jq("#getReferrals").on("click", function (e) {
       			e.preventDefault();
       			//Run the fetch tasks
      console.log('Starting the fetch task!');
      jQuery.getJSON('${ ui.actionLink("kenyaemrIL", "referralsDataExchange", "pullCommunityReferralsFromFhir")}')
             .success(function (data) {
              if(data.status === "Success") {
                  // Hide spinner
                  //display_loading_spinner(false, 'wait-loading');
                  console.log("Data ==>"+data);
                  console.log(data.message);
                  setTimeout(function (){
                      location.reload();
                  }, 2000);




              }else{
                  console.log("Data ==>"+data);
              }
             })
          .fail(function (err) {
              console.log("Error fetching referral records: " + JSON.stringify(err));
              }
          )
      });
      var activeReferralsTbl = jq('#activeReferralsTbl').DataTable();
      var completedReferralsTbl = jq('#completedReferralsTbl').DataTable();
    });
</script>
<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>
<div class="ke-page-content">
    <div style="float: right;">
        <button id="getReferrals" class="ui-state-default">Pull Referrals</button>
    </div>
    <div id="ftabs">
      <ul>
          <li id="activefTFacilitiesR"><a href="#afToFacilitiesReferrals">Active referral to this facility</a></li>
          <li id="servicedfTFacilitiesR"><a href="#sfToFacilitiesReferrals">Serviced referral to this facility</a></li>
          <li id="tFacilitiesR"><a href="#toFacilitiesReferrals">Referral from this facility to other facilities</a></li>
          <li id="fFacilitiesR"><a href="#fromFacilitiesReferrals">Referral from this facility to community</a></li>
      </ul>
      <div id="afToFacilitiesReferrals">
            ${ ui.includeFragment("patientqueueapp", "referral/activeFromFacilitiesAndCommunityUnits")}
      </div>
      <div id="sfToFacilitiesReferrals">
            ${ ui.includeFragment("patientqueueapp", "referral/servicedFromFacilitiesAndCommunityUnits")}
      </div>
      <div id="toFacilitiesReferrals">
            ${ ui.includeFragment("patientqueueapp", "referral/fromFacilityToFacilitiesReferral")}
      </div>
      <div id="fromFacilitiesReferrals">
        ${ ui.includeFragment("patientqueueapp", "referral/fromFacilitiesToCommunityReferrals") }
      </div>
    </div>
</div>