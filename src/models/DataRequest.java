package models;

import java.io.Serializable;

/**
 * Represents a data request to the server.
 * Intended to tell the server that it wishes to retrieve
 * plant information for a user.
 */
public class DataRequest implements Serializable {

	private static final long serialVersionUID = 1816313010023085287L;
	private Login requestingUser;

    public DataRequest(Login requestingUser) {
        this.requestingUser = requestingUser;
    }

    public Login getRequestingUser() {
        return requestingUser;
    }
}
