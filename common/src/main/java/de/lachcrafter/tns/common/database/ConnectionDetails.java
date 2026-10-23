package de.lachcrafter.tns.common.database;

public record ConnectionDetails(String url, String user, String database, String password) {

    String getFormattedUrlWithDatabase() {

        return "jdbc:mysql://" + url + "/" + database;
    }

}
