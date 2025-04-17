package org.example.camel101.example.misolution.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class SftpPushUriBuilderProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String host = "mc82m0sycp8ynx4fqynw-63lx470.ftp.marketingcloudops.com";
        String port = "22";
        String username = "546001989_7";
        String password = "Bill@milvuS!2024#";
        String directoryName = "/Import/20240124";

        String unique = UUID.randomUUID().toString();
        String fileName = unique + "__output.csv";

        String sftpUri = String.format("sftp://%s@%s:%s/%s?password=%s&preferredAuthentications=publickey,password&serverHostKeys=ssh-rsa&fileName=%s",
                username,
                host,
                port,
                directoryName,
                password,
                fileName
        );

        exchange.getIn().setHeader("sftpUri", sftpUri);
    }
}