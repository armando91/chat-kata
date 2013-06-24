import static org.junit.Assert.*;

import org.junit.Test;

import chat.kata.ChatController;

@TestFor(UrlMappings)
@Mock([ChatController])
class UrlMappingsTest {

	@Test
	public void testChatListURLMapping() {
		assertRestForwardUrlMapping("GET", "/api/chat", controller:"chat", action:"list")
	}

	@Test
	public void testChatSendURLMapping() {
		assertRestForwardUrlMapping("POST", "/api/chat", controller:"chat", action:"send")
	}
	
	/* ------------------- helper methods -- */
	private void assertRestForwardUrlMapping(assertions, String method, String url) {
		assertRestForwardUrlMapping(assertions, method, url, null)
	}

	private void assertRestForwardUrlMapping(assertions, String method, url, paramAssertions) {
		webRequest.currentRequest.setMethod(method)
		assertForwardUrlMapping(assertions, url, paramAssertions)
	}
	

}
