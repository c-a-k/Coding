public class KeyNotFoundException extends Exception {
		/**
	  	 * Creates an exception with a message.
	 	 * @param msg The message.
		 */
		public KeyNotFoundException(String msg) {
			super(msg);
		}

		/**
		 * Creates an exception.
	 	 */
		public KeyNotFoundException() {
			super();
		}
}
