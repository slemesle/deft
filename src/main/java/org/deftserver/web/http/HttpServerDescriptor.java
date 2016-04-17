package org.deftserver.web.http;

/**
 * This class provides a possiblity to change the tunables used by Deft for the http server configuration.
 * Do not change the values unless you know what you are doing.
 */
public class HttpServerDescriptor {

	/** The number of seconds Deft will wait for subsequent socket activity before closing the connection */
	public static int KEEP_ALIVE_TIMEOUT = 30 * 1000;	// 30s

	/**
	 * Size of the read (receive) buffer.
	 * "Ideally, an HTTP request should not go beyond 1 packet.
	 * The most widely used networks limit packets to approximately 1500 bytes, so if you can constrain each request
	 * to fewer than 1500 bytes, you can reduce the overhead of the request stream." (from: http://bit.ly/bkksUu)
	 */
	public static int READ_BUFFER_SIZE = 1024;	// 1024 bytes

	/**
	 * Size of the write (send) buffer.
	 */
	public static int WRITE_BUFFER_SIZE = 1024;	// 1024 bytes

	public static int REQUEST_LINE_MAX_SIZE = 500;

	public static int MAX_HEADER_LINE_COUNT = 30;

}
