package br.gov.sp.cps.api.pixel.core.service;

import br.gov.sp.cps.api.pixel.core.domain.dto.DadosRelatorioDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioService {

    public static InputStreamResource exportarDadosLoteUsuario(List<DadosRelatorioDTO> dadosRelatorio) throws IOException {

        String[] HEADERS={
                "Nome da Fazenda", "Espécie", "Área Plantada", "Data do Plantio",
                "Custo Esperado", "Status", "Temperatura Ambiente", "Temperatura Solo",
                "Umidade Ambiente", "Umidade Solo", "pH do Solo", "Índice UV", "Data da Atualização"
        };

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream();) {

            Sheet sheet = workbook.createSheet("data");
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(HEADERS[i]);
            }

            int rowIndex = 1;
            for (DadosRelatorioDTO dto : dadosRelatorio) {
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(dto.fazendaNome());
                row.createCell(1).setCellValue(dto.especieNome());
                row.createCell(2).setCellValue(dto.areaPlantada());
                row.createCell(3).setCellValue(dto.dataPlantio().toString());
                row.createCell(4).setCellValue(dto.custoEsperado());
                row.createCell(5).setCellValue(dto.status().toString());
                row.createCell(6).setCellValue(dto.temperaturaAmbiente());
                row.createCell(7).setCellValue(dto.temperaturaSolo());
                row.createCell(8).setCellValue(dto.umidadeAmbiente());
                row.createCell(9).setCellValue(dto.umidadeSolo());
                row.createCell(10).setCellValue(dto.phSolo());
                row.createCell(11).setCellValue(dto.indiceUV());
                row.createCell(12).setCellValue(dto.dataAtualizacao().toString());
            }

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao exportar os dados do lote em .xlsx", e);
        }
    }

    public String gerarNomeArquivo(DadosRelatorioDTO dadosRelatorio) {
        return dadosRelatorio.fazendaNome() + "_" + LocalDate.now() + ".xlsx";
    }
}