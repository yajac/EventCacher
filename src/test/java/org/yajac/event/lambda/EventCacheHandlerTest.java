package org.yajac.event.lambda;

import org.junit.Assert;
import org.junit.Test;
import org.yajac.BaseTestClass;
import org.yajac.aws.lambda.model.GatewayRequest;
import org.yajac.aws.lambda.model.GatewayResponse;

public class EventCacheHandlerTest extends BaseTestClass{

    private EventCacheHandler eventCacheHandler = new EventCacheHandler();

    @Test
    public void handleRequest() throws Exception {
        GatewayRequest request = new GatewayRequest();
        GatewayResponse response = eventCacheHandler.handleRequest(request, getContext());
        Assert.assertNotNull(response);
        String body = response.getBody();
        Assert.assertNotNull(body);
        Assert.assertEquals(body, "{'Response': 'OK'}");
    }

}