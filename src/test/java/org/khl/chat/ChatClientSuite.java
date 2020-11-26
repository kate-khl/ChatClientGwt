package org.khl.chat;

import org.khl.chat.client.ChatClientTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ChatClientSuite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for ChatClient");
    suite.addTestSuite(ChatClientTest.class);
    return suite;
  }
}
