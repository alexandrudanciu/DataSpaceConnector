/*
 *  Copyright (c) 2022 Microsoft Corporation
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

package org.eclipse.dataspaceconnector.azure.dataplane.azurestorage.pipeline;

import com.github.javafaker.Faker;
import net.jodah.failsafe.RetryPolicy;
import org.eclipse.dataspaceconnector.azure.dataplane.azurestorage.adapter.BlobAdapterFactory;
import org.eclipse.dataspaceconnector.azure.dataplane.azurestorage.schema.AzureBlobStoreSchema;
import org.eclipse.dataspaceconnector.spi.monitor.Monitor;
import org.eclipse.dataspaceconnector.spi.types.domain.DataAddress;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.DataFlowRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.dataspaceconnector.azure.dataplane.azurestorage.pipeline.AzureStorageTestFixtures.createAccountName;
import static org.eclipse.dataspaceconnector.azure.dataplane.azurestorage.pipeline.AzureStorageTestFixtures.createBlobName;
import static org.eclipse.dataspaceconnector.azure.dataplane.azurestorage.pipeline.AzureStorageTestFixtures.createContainerName;
import static org.eclipse.dataspaceconnector.azure.dataplane.azurestorage.pipeline.AzureStorageTestFixtures.createRequest;
import static org.eclipse.dataspaceconnector.azure.dataplane.azurestorage.pipeline.AzureStorageTestFixtures.createSharedKey;
import static org.mockito.Mockito.mock;

class AzureStorageDataSourceFactoryTest {
    static Faker faker = new Faker();
    BlobAdapterFactory blobAdapterFactory = mock(BlobAdapterFactory.class);
    AzureStorageDataSourceFactory factory = new AzureStorageDataSourceFactory(blobAdapterFactory, new RetryPolicy<>(), mock(Monitor.class));
    DataFlowRequest.Builder request = createRequest(AzureBlobStoreSchema.TYPE);
    DataFlowRequest.Builder invalidRequest = createRequest(faker.lorem().word());
    DataAddress.Builder dataAddress = DataAddress.Builder.newInstance().type(AzureBlobStoreSchema.TYPE);

    String accountName = createAccountName();
    String containerName = createContainerName();
    String blobName = createBlobName();
    String sharedKey = createSharedKey();

    @Test
    void canHandle_whenBlobRequest_returnsTrue() {
        assertThat(factory.canHandle(request.build())).isTrue();
    }

    @Test
    void canHandle_whenNotBlobRequest_returnsFalse() {
        assertThat(factory.canHandle(invalidRequest.build())).isFalse();
    }

    @Test
    void validate_whenRequestValid_succeeds() {
        assertThat(factory.validate(request.sourceDataAddress(dataAddress
                                .property(AzureBlobStoreSchema.ACCOUNT_NAME, accountName)
                                .property(AzureBlobStoreSchema.CONTAINER_NAME, containerName)
                                .property(AzureBlobStoreSchema.BLOB_NAME, blobName)
                                .property(AzureBlobStoreSchema.SHARED_KEY, sharedKey)
                                .build())
                        .build())
                .succeeded()).isTrue();
    }

    @Test
    void validate_whenMissingAccountName_fails() {
        assertThat(factory.validate(request.sourceDataAddress(dataAddress
                                .property(AzureBlobStoreSchema.CONTAINER_NAME, containerName)
                                .property(AzureBlobStoreSchema.BLOB_NAME, blobName)
                                .property(AzureBlobStoreSchema.SHARED_KEY, sharedKey)
                                .build())
                        .build())
                .failed()).isTrue();
    }

    @Test
    void validate_whenMissingContainerName_fails() {
        assertThat(factory.validate(request.sourceDataAddress(dataAddress
                                .property(AzureBlobStoreSchema.ACCOUNT_NAME, accountName)
                                .property(AzureBlobStoreSchema.BLOB_NAME, blobName)
                                .property(AzureBlobStoreSchema.SHARED_KEY, sharedKey)
                                .build())
                        .build())
                .failed()).isTrue();
    }

    @Test
    void validate_whenMissingBlobName_fails() {
        assertThat(factory.validate(request.sourceDataAddress(dataAddress
                                .property(AzureBlobStoreSchema.ACCOUNT_NAME, accountName)
                                .property(AzureBlobStoreSchema.CONTAINER_NAME, containerName)
                                .property(AzureBlobStoreSchema.SHARED_KEY, sharedKey)
                                .build())
                        .build())
                .failed()).isTrue();
    }

    @Test
    void validate_whenMissingSharedKey_fails() {
        assertThat(factory.validate(request.sourceDataAddress(dataAddress
                                .property(AzureBlobStoreSchema.ACCOUNT_NAME, accountName)
                                .property(AzureBlobStoreSchema.CONTAINER_NAME, containerName)
                                .property(AzureBlobStoreSchema.BLOB_NAME, blobName)
                                .build())
                        .build())
                .failed()).isTrue();
    }

    @Test
    void createSource_whenValidRequest_succeeds() {
        var validRequest = request.sourceDataAddress(dataAddress
                .property(AzureBlobStoreSchema.ACCOUNT_NAME, accountName)
                .property(AzureBlobStoreSchema.CONTAINER_NAME, containerName)
                .property(AzureBlobStoreSchema.BLOB_NAME, blobName)
                .property(AzureBlobStoreSchema.SHARED_KEY, sharedKey)
                .build());
        assertThat(factory.createSource(validRequest.build())).isNotNull();
    }
}
