/*
 *  Copyright (c) 2020 - 2022 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */

package org.eclipse.dataspaceconnector.transfer.provision.http.webhook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.eclipse.dataspaceconnector.spi.types.domain.DataAddress;
import org.eclipse.dataspaceconnector.spi.types.domain.Polymorphic;

@JsonDeserialize(builder = ProvisionerWebhookRequest.Builder.class)
@JsonTypeName("dataspaceconnector:provisioner-callback-request")
public class ProvisionerWebhookRequest implements Polymorphic {
    private String resourceDefinitionId;
    private boolean hasToken;
    private String assetId;
    private String resourceName;
    private DataAddress contentDataAddress;
    private String apiKeyJwt;

    private ProvisionerWebhookRequest() {
    }

    public String getResourceName() {
        return resourceName;
    }

    public DataAddress getContentDataAddress() {
        return contentDataAddress;
    }

    public String getAssetId() {
        return assetId;
    }


    public String getResourceDefinitionId() {
        return resourceDefinitionId;
    }

    @JsonProperty("hasToken")
    public boolean hasToken() {
        return hasToken;
    }

    public String getApiToken() {
        return apiKeyJwt;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private final ProvisionerWebhookRequest request;

        private Builder() {
            request = new ProvisionerWebhookRequest();
        }

        @JsonCreator
        public static Builder newInstance() {
            return new Builder();
        }

        public Builder assetId(String assetId) {
            request.assetId = assetId;
            return this;
        }

        public Builder resourceName(String resourceName) {
            request.resourceName = resourceName;
            return this;
        }

        public Builder contentDataAddress(DataAddress contentDataAddress) {
            request.contentDataAddress = contentDataAddress;
            return this;
        }

        public Builder resourceDefinitionId(String resourceDefinitionId) {
            request.resourceDefinitionId = resourceDefinitionId;
            return this;
        }

        public Builder hasToken(boolean hasToken) {
            request.hasToken = hasToken;
            return this;
        }

        public Builder apiToken(String token) {
            request.apiKeyJwt = token;
            return this;
        }

        public ProvisionerWebhookRequest build() {
            return request;
        }
    }
}
