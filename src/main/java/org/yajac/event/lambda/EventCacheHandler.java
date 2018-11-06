package org.yajac.event.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.yajac.aws.lambda.handler.LambdaHandler;
import org.yajac.aws.lambda.model.GatewayRequest;
import org.yajac.aws.lambda.model.GatewayResponse;

public class EventCacheHandler extends LambdaHandler implements RequestHandler<GatewayRequest, GatewayResponse> {

    protected final static String body = "{\"'Response': \"OK\"}";

    protected String getBody(GatewayRequest request) {
        return body;
    }
}
