package com.thebund1st.daming.boot.aliyun

import com.aliyuncs.*
import com.aliyuncs.auth.Credential
import com.aliyuncs.auth.Signer
import com.aliyuncs.exceptions.ClientException
import com.aliyuncs.exceptions.ServerException
import com.aliyuncs.http.FormatType
import com.aliyuncs.http.HttpResponse
import com.aliyuncs.profile.IClientProfile
import com.aliyuncs.regions.Endpoint

class CustomizedAcsClient implements IAcsClient {

    @Override
    def <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request) throws ClientException, ServerException {
        return null
    }

    @Override
    def <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request, boolean autoRetry, int maxRetryCounts) throws ClientException, ServerException {
        return null
    }

    @Override
    def <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request, IClientProfile profile) throws ClientException, ServerException {
        return null
    }

    @Override
    def <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request, String regionId, Credential credential) throws ClientException, ServerException {
        return null
    }

    @Override
    def <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request) throws ServerException, ClientException {
        return null
    }

    @Override
    def <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, boolean autoRetry, int maxRetryCounts) throws ServerException, ClientException {
        return null
    }

    @Override
    def <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, IClientProfile profile) throws ServerException, ClientException {
        return null
    }

    @Override
    def <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, String regionId, Credential credential) throws ServerException, ClientException {
        return null
    }

    @Override
    def <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, String regionId) throws ServerException, ClientException {
        return null
    }

    @Override
    CommonResponse getCommonResponse(CommonRequest request) throws ServerException, ClientException {
        return null
    }

    @Override
    def <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request, boolean autoRetry, int maxRetryCounts, IClientProfile profile) throws ClientException, ServerException {
        return null
    }

    @Override
    def <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request, boolean autoRetry, int maxRetryNumber, String regionId, Credential credential, Signer signer, FormatType format, List<Endpoint> endpoints) throws ClientException, ServerException {
        return null
    }

    @Override
    void restoreSSLCertificate() {

    }

    @Override
    void ignoreSSLCertificate() {

    }

    @Override
    void shutdown() {

    }
}
