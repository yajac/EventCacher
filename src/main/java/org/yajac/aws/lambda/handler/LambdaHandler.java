package org.yajac.aws.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.yajac.aws.lambda.model.GatewayRequest;
import org.yajac.aws.lambda.model.GatewayResponse;

/**
 * Handler for requests to Lambda function.
 */
public class LambdaHandler implements RequestHandler<GatewayRequest, GatewayResponse> {

    /**
     * Handle Request for Lambda
     * @param input
     * @param context
     * @return GatewayResponse response
     */
    public GatewayResponse handleRequest(final GatewayRequest input, final Context context) {
        GatewayResponse response;
        try {
            String body = getBody(input);
            context.getLogger().log("Body: " + body);
            response = new GatewayResponse(body, 200);
        } catch (Exception e){
            context.getLogger().log("Error: " + e);
            response = new GatewayResponse("{'Error':'" + e.getMessage() + "'}", 500);
        }
        response.getHeaders().put("Content-Type", "application/json");
        return response;
    }

    protected String getBody(GatewayRequest request) {
        return "[]";
    }
}
