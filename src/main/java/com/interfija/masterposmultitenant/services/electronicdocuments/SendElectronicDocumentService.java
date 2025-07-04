package com.interfija.masterposmultitenant.services.electronicdocuments;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.electronicdocuments.ResolutionDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SendElectronicDocumentService {

    public static String HTTP_API = "https://api.solucionesempresarialescol.com/api/ubl2.1/";
    public final static String CREDIT_NOTE_API = "credit-note";
    public final static String INVOICE_API = "invoice";
    public final static String INVOICE_POS_API = "eqdoc";
    public final static String SUPPORT_ADJUSTMENT_API = "sd-credit-note";

    public boolean sendDocument(InvoiceDTO invoiceDTO, CompanyDTO companyDTO, BranchDTO branchDTO,
                                ResolutionDTO resolutionDTO, Short idTypeElectronicDocument) throws Exception {
        String url = getUrl(INVOICE_API, "");
        ObjectNode json = new ElectronicDocumentJson(invoiceDTO, companyDTO, branchDTO, resolutionDTO, idTypeElectronicDocument).getJson();
        String keyApi = "cb13ae1fcb7c2156c209042efca97d87d1311b8a2601900767b9685ea5243943";

        ObjectNode response = requestPost(url, json, ObjectNode.class, keyApi);
        if (response == null) {
            throw new Exception("responde null");
        }

        boolean successApi = response.get("success").asBoolean();
        String messageApi = response.get("message").asText();

        if (!successApi) {
            return false;
        }

        boolean IsValidDian = response.get("ResponseDian").get("Envelope").get("Body").get("SendBillSyncResponse")
                .get("SendBillSyncResult").get("IsValid").asBoolean();

        String statusCodeDian = response.get("ResponseDian").get("Envelope").get("Body").get("SendBillSyncResponse")
                .get("SendBillSyncResult").get("StatusCode").asText();

        boolean successDian = IsValidDian && "00".equals(statusCodeDian);

        String messageDian = response.get("ResponseDian").get("Envelope").get("Body")
                .get("SendBillSyncResponse").get("SendBillSyncResult").get("StatusMessage").asText();

        JsonNode stringNode = response.get("ResponseDian").get("Envelope").get("Body").get("SendBillSyncResponse")
                .get("SendBillSyncResult").get("ErrorMessage").get("string");

        String errorDian;

        if (stringNode.isArray()) {
            errorDian = StreamSupport.stream(stringNode.spliterator(), false)
                    .map(JsonNode::asText)
                    .collect(Collectors.joining("\n"));
        } else {
            errorDian = stringNode.asText();
        }

        if (successDian) {
            String key = switch (idTypeElectronicDocument) {
                case 4 -> "cude";
                case 0 -> "cuds";
                default -> "cufe";
            };

            String codeUnique = response.get(key).asText();
            String xml = response.get("urlinvoicexml").asText();
            String urlPdf = response.get("urlinvoicepdf").asText();
            String qrCode = response.get("QRStr").asText();
        }

        return successDian;
    }

    private String getUrl(String typeSend, String setTest) {
        //if (typeEnvironment.equals("PRUEBAS") || typeEnvironment.equals("PRODUCCION")) {
        if (true) {
            return HTTP_API.concat(typeSend);
        } else {
            return HTTP_API.concat(typeSend).concat("/").concat(setTest);
        }
    }

    /**
     * Realiza el envio de la factura electronica a la api por medio de una peticion post
     */
    protected ObjectNode requestPost(String url, ObjectNode json, Class<ObjectNode> responseType, String keyApi) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Authorization", "Bearer " + keyApi);
        headers.put("content-type", "application/json");
        headers.put("cache-control", "no-cache");
        headers.put("accept", "application/json");
        return HttpUtil.post(url, json, responseType, headers);
    }

}
