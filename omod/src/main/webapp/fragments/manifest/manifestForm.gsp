<%
    ui.decorateWith("kenyaui", "panel", [heading: (command.original ? "Edit" : "Add") + " Lab Manifest", frameOnly: true])

    def manifestCoverage = [
            [
                    [object: command, property: "startDate", label: "Start Date"],
                    [object: command, property: "endDate", label: "End Date"],

            ]
    ]


    def manifestDispatch = [
            [

                    [object: command, property: "dispatchDate", label: "Dispatch Date"],
                    [object: command, property: "courier", label: "Courier Name"],
                    [object: command, property: "courierOfficer", label: "Person handed to"]

            ]
    ]

%>

<form id="new-edit-manifest-form" method="post"
      action="${ui.actionLink("kenyaemrorderentry", "manifest/manifestForm", "saveManifest")}">
    <% if (command.original) { %>
    <input type="hidden" name="manifestId" value="${command.original.id}"/>
    <% } %>

    <div class="ke-panel-content">

        <div class="ke-form-globalerrors" style="display: none"></div>

        <div class="ke-form-instructions">
            <strong>*</strong> indicates a required field
        </div>

        <fieldset>
            <legend>Manifest date range</legend>

            <% manifestCoverage.each { %>
            ${ui.includeFragment("kenyaui", "widget/rowOfFields", [fields: it])}
            <% } %>
        </fieldset>
        <fieldset>
            <legend>Dispatch Details</legend>
            <% manifestDispatch.each { %>
            ${ui.includeFragment("kenyaui", "widget/rowOfFields", [fields: it])}
            <% } %>
        </fieldset>

        <fieldset>
            <legend>Mark manifest as ready to be sent</legend>
            <table>
                <tr>
                    <td class="ke-field-label">Ready to send</td>
                </tr>
                <tr>
                    <td>
                        <select name="status">
                            <option></option>
                            <% manifestStatusOptions.each { %>
                            <option ${(command.status == null)? "" : it == command.status ? "selected" : ""} value="${it}">${it}</option>
                            <% } %>
                        </select>
                    </td>
                </tr>
            </table>
        </fieldset>

        <div class="ke-panel-footer">
            <button type="submit">
                <img src="${ui.resourceLink("kenyaui", "images/glyphs/ok.png")}"/> ${command.original ? "Save Changes" : "Create Lab Manifest"}
            </button>

            <button type="button" class="cancel-button"><img
                    src="${ui.resourceLink("kenyaui", "images/glyphs/cancel.png")}"/> Cancel</button>

        </div>
    </div>
</form>


<script type="text/javascript">

    //On ready
    jQuery(function () {
        //defaults
        jQuery('#new-edit-manifest-form .cancel-button').click(function () {
            ui.navigate('${ config.returnUrl }');
        });
        kenyaui.setupAjaxPost('new-edit-manifest-form', {
            onSuccess: function (data) {
                if (data.manifestId) {
                    ui.navigate('kenyaemrorderentry', 'orders/labOrdersManifestHome');
                } else {
                    kenyaui.notifyError('Saving manifest was successful, but with unexpected response');
                }
            }
        });

    }); // end of jQuery initialization bloc


</script>