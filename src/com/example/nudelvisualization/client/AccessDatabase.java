package com.example.nudelvisualization.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface AccessDatabase extends RemoteService {
  ArrayList<String> getMetaData(String input) throws IllegalArgumentException;
}
