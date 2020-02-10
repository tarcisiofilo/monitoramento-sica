package com.sica.monitoramento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sica.service.MedicaoAutomaticaInstrumentoService;

@Service
public class LeitorInstrumentosMonitoramentoService implements Runnable {

	private String identificador;

	private String api;

	@Autowired
	private MedicaoAutomaticaInstrumentoService medicaoAutomaticaInstrumentoService;

	@Override
	public void run() {
		RestTemplate restTemplate = new RestTemplate();
		Double valor = restTemplate.getForObject(api, Double.class);
		this.medicaoAutomaticaInstrumentoService.salvarMedicao(valor, identificador);
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

}
