package com.example.nudelvisualization.client;

import java.io.File;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface saveConfigAsync {

	void setConfigFromFile(AsyncCallback<Configuration> callback);

	void getConfigAsFile(Configuration config, AsyncCallback<Void> callback);

}
