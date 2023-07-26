<%
    ui.decorateWith("kenyaemr", "standardPage", [layout: "sidebar"])
    ui.includeJavascript("kenyaemrorderentry", "jquery.twbsPagination.min.js")
    ui.includeJavascript("kenyaemrorderentry", "ordersUtils.js")

    ui.includeJavascript("kenyaemrorderentry", "bootstrap/bootstrap.bundle.min.js")
    ui.includeCss("kenyaemrorderentry", "bootstrap/bootstrap-iso.css")

    def menuItems = [
            [label: "Back to home", iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to home", href: ui.pageLink("kenyaemr", "userHome")]
    ]

    def manifestCategories = [
            [label: "Summary", iconProvider: "kenyaui", icon: "", label: "Summary", href: ui.pageLink("kenyaemrorderentry", "orders/labOrdersManifestHome")],
            [label: "Draft", iconProvider: "kenyaui", icon: "", label: "Draft", href: ui.pageLink("kenyaemrorderentry", "orders/labOrdersDraftManifestHome")],
            [label: "Ready to send", iconProvider: "kenyaui", icon: "", label: "Ready to send", href: ui.pageLink("kenyaemrorderentry", "orders/labOrdersReadyToSendManifestHome")],
            [label: "On hold", iconProvider: "kenyaui", icon: "", label: "On hold", href: ui.pageLink("kenyaemrorderentry", "orders/labOrdersOnHoldManifestHome")],
            [label: "Sending", iconProvider: "kenyaui", icon: "", label: "Sending", href: ""],
            [label: "Submitted", iconProvider: "kenyaui", icon: "", label: "Submitted", href: ui.pageLink("kenyaemrorderentry", "orders/labOrdersSubmittedManifestHome")],
            [label: "Incomplete With Errors", iconProvider: "kenyaui", icon: "", label: "Incomplete With Errors", href: ui.pageLink("kenyaemrorderentry", "orders/labOrdersIncompleteWithErrorResultsManifestHome")],
            [label: "Incomplete results", iconProvider: "kenyaui", icon: "", label: "Incomplete results", href: ui.pageLink("kenyaemrorderentry", "orders/labOrdersIncompleteResultManifestHome")],
            [label: "Complete With Errors", iconProvider: "kenyaui", icon: "", label: "Complete With Errors", href: ui.pageLink("kenyaemrorderentry", "orders/labOrdersCompleteWithErrorResultsManifestHome")],
            [label: "Complete results", iconProvider: "kenyaui", icon: "", label: "Complete results", href: ""],
    ]

    def actionRequired = [
            [label: "Collect new sample", iconProvider: "kenyaui", icon: "", label: "Collect new sample", href: ui.pageLink("kenyaemrorderentry", "orders/manifestOrdersCollectSampleHome")],
            [label: "Missing samples", iconProvider: "kenyaui", icon: "", label: "Missing samples", href: ui.pageLink("kenyaemrorderentry", "orders/manifestOrdersMissingSamplesHome")],
    ]

    def configuration = [
            [label: "Settings", iconProvider: "kenyaui", icon: "", label: "Settings", href: ""],
    ]
%>

<div class="ke-page-sidebar">
    ${ui.includeFragment("kenyaui", "widget/panelMenu", [heading: "Back", items: menuItems])}
    ${ui.includeFragment("kenyaui", "widget/panelMenu", [heading: "Manifest status", items: manifestCategories])}
    ${ui.includeFragment("kenyaui", "widget/panelMenu", [heading: "Action required", items: actionRequired])}
    ${ui.includeFragment("kenyaui", "widget/panelMenu", [heading: "Configuration", items: configuration])}
</div>

<div class="ke-page-content">
    <div align="left">

        <h2 style="color:steelblue">Settings</h2>

        <div class="bootstrap-iso container px-5">
            <table class="bootstrap-iso table table-hover table-success table-striped table-bordered border-primary">
                <thead class="table-danger">
                    <tr>
                        <th scope="col">Setting</th>
                        <th scope="col">Value</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>System Type (${ gpSystemType }):</td>
                        <td>${ SystemType }</td>
                    </tr>
                    <tr>
                        <td class="table-primary" colspan="2"><p class="fw-bold">EID:</p></td>
                    </tr>
                    <tr>
                        <td>EID Enabled (${ gpEnableEIDFunction }):</td>
                        <td>${ EnableEIDFunction }</td>
                    </tr>
                    <tr>
                        <td>TOKEN (edarp_eid_server_api_token):</td>
                        <td>kyHNSAer443anou3YoqE5</td>
                    </tr>
                    <tr>
                        <td>PULL URL (edarp_eid_server_result_url):</td>
                        <td>http://41.203.216.114:81/kenyaemr/vltest/vl_results</td>
                    </tr>
                    <tr>
                        <td>PUSH URL (edarp_eid_server_url):</td>
                        <td>http://41.203.216.114:81/kenyaemr/vltest/receive</td>
                    </tr>
                    <tr>
                        <td class="table-primary" colspan="2"><p class="fw-bold">VL:</p></td>
                    </tr>
                    <tr>
                        <td>TOKEN (edarp_vl_server_api_token):</td>
                        <td>kyHNSAer443anou3YoqE5</td>
                    </tr>
                    <tr>
                        <td>PULL URL (edarp_vl_server_result_url):</td>
                        <td>http://41.203.216.114:81/kenyaemr/vltest/vl_results</td>
                    </tr>
                    <tr>
                        <td>PUSH URL (edarp_vl_server_url):</td>
                        <td>http://41.203.216.114:81/kenyaemr/vltest/receive</td>
                    </tr>
                    <tr>
                        <td class="table-primary" colspan="2"><p class="fw-bold">COMMS:</p></td>
                    </tr>
                    <tr>
                        <td>Local Endpoint (local.viral_load_result_end_point):</td>
                        <td>http://127.0.0.1:8080/openmrs/ws/rest/v1/kemrorder/labresults</td>
                    </tr>
                    <tr>
                        <td>Endpoint User Name (scheduler.username):</td>
                        <td>Admin</td>
                    </tr>
                    <tr>
                        <td>Endpoint Password (scheduler.password):</td>
                        <td>Admin123</td>
                    </tr>
                    <tr>
                        <td>SSL Verification Enabled (kemrorder.ssl_verification_enabled):</td>
                        <td>true</td>
                    </tr>
                    <tr>
                        <td class="table-primary" colspan="2"><p class="fw-bold">TIMING:</p></td>
                    </tr>
                    <tr>
                        <td>TAT in Days (kemrorder.viral_load_result_tat_in_days):</td>
                        <td>10</td>
                    </tr>
                    <tr>
                        <td>Retry period in Days for incomplete orders (kemrorder.retry_period_for_incomplete_vl_result):</td>
                        <td>5</td>
                    </tr>
                    <tr>
                        <td class="table-primary" colspan="2"><p class="fw-bold">SCHEDULERS:</p></td>
                    </tr>
                    <tr>
                        <td>PUSH Scheduler (org.openmrs.module.kenyaemrorderentry.task.PushLabRequestsTask):</td>
                        <td class="bootstrap-iso p-0">
                            <table class="bootstrap-iso table table-hover table-success table-striped table-bordered border-primary m-0">
                                <tbody>
                                    <tr>
                                        <td>Started: Yes</td>
                                        <td>Interval: 30 Min</td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>PULL Scheduler (org.openmrs.module.kenyaemrorderentry.task.PullViralLoadLabResultsTask):</td>
                        <td class="bootstrap-iso p-0">
                            <table class="bootstrap-iso table table-hover table-success table-striped table-bordered border-primary m-0">
                                <tbody>
                                    <tr>
                                        <td>Started: Yes</td>
                                        <td>Interval: 20 Min</td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
                <tfoot>
                    <td colspan="2">
                        <button type="button" class="btn btn-primary btn-sm float-end">
                            EDIT
                        </button>
                    </td>
                </tfoot>
            </table>
        </div>
    </div>

</div>

<script type="text/javascript">

    //On ready
    jq = jQuery;
    jq(function () {
        // mark the activePage
        showActivePageOnManifestNavigation('Settings');
        
    });

</script>