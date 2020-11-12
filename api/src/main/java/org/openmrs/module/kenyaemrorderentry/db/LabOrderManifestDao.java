package org.openmrs.module.kenyaemrorderentry.db;

import org.openmrs.module.kenyaemrorderentry.LabOrderManifest;

import java.util.Date;
import java.util.List;

/**
 * Created by developer on 09 Nov, 2020
 */

public interface LabOrderManifestDao {
LabOrderManifest saveLabOrderManifest(LabOrderManifest labOrderManifest);
List<LabOrderManifest> getLabOrderManifest();
LabOrderManifest getLabOrderManifestById(Integer id);
List<LabOrderManifest> getLabOrderManifestBetweenDates(Date startDate, Date endDate);
void voidLabOrderManifest(Integer id);
}
