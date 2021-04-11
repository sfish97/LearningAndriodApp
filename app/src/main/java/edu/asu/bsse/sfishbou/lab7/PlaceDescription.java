package edu.asu.bsse.sfishbou.lab7;

/**
 * Copyright (c) 2021 Steven Fishbough,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

 * @author Steven Fishbough sfishbou@asu.edu
 *         Software Engineering, CIDSE, IAFSE, Arizona State University Polytechnic
 * @version April 11, 2021
 */
public class PlaceDescription {

    private String name;
    private String description;
    private String category;
    private String address_Title;
    private String address_Street;
    private String elevation;
    private String latitude;
    private String longitude;

    public PlaceDescription(){ }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getAddress_Title() { return address_Title; }

    public void setAddress_Title(String address_Title) { this.address_Title = address_Title; }

    public String getAddress_Street() { return address_Street; }

    public void setAddress_Street(String address_Street) { this.address_Street = address_Street; }

    public String getElevation() { return elevation; }

    public void setElevation(String elevation) { this.elevation = elevation; }

    public String getLatitude() { return latitude; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }

    public void setLongitude(String longitude) { this.longitude = longitude; }


}
