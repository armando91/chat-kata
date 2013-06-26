package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.model.RequestError;

/**
 * Empty response handler.
 * 
 * Base class for Net Response handlers.
 * 
 * @author startic
 * 
 * @param <Response>
 */
public class NetResponseHandler<Response> {

	Response datos; 
	/**
	 * Handles a successful request
	 * */
	public void onSuccess(Response response) {
		datos = response;
	}

	public Response getDatos() {
		return datos;
	}

	/**
	 * Handles a network error.
	 */
	public void onNetError() {
	}

	/**
	 * Handles a request error.
	 */
	public void onRequestError(RequestError error) {
	}
}
