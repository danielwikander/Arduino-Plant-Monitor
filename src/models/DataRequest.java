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

	/**
	 * Constructor creates a DataRequest with giver User-object a key to access the data in the database.
	 * @param requestingUser A representation of a user making a request to its own plant's.
	 */
    public DataRequest(Login requestingUser) {
        this.requestingUser = requestingUser;
    }

    /**
     * Method returns the user-data of the requesting user.
     * @return A representation of the user making the request.
     */
    public Login getRequestingUser() {
        return requestingUser;
    }
}
