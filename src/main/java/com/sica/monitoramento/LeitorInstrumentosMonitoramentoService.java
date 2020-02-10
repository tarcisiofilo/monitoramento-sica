package com.sica.monitoramento;

import java.time.ZonedDateTime;

import com.sica.domain.MedicaoInstrumento;
import com.sica.repository.InstrumentoMonitoramentoRepository;
import com.sica.repository.MedicaoInstrumentoRepository;

public class LeitorInstrumentosMonitoramentoService implements Runnable {

	private String identificador;

	private String api;

	private InstrumentoMonitoramentoRepository instrumentoMonitoramentoRepository;

	private MedicaoInstrumentoRepository medicaoInstrumentoRepository;

	public LeitorInstrumentosMonitoramentoService(String identificador, String api,
			InstrumentoMonitoramentoRepository instrumentoMonitoramentoRepository,
			MedicaoInstrumentoRepository medicaoInstrumentoRepository) {
		this.api = api;
		this.identificador = identificador;
		this.medicaoInstrumentoRepository = medicaoInstrumentoRepository;
		this.instrumentoMonitoramentoRepository = instrumentoMonitoramentoRepository;
	}

	@Override
	public void run() {
		System.out.println(api);
		System.out.println(identificador);
		MedicaoInstrumento medicaoInstrumento = new MedicaoInstrumento();
		medicaoInstrumento
				.setInstrumentoMonitoramento(instrumentoMonitoramentoRepository.findByIdentificao(this.identificador));
		medicaoInstrumento.setValor(1D);
		medicaoInstrumento.setData(ZonedDateTime.now());
		this.medicaoInstrumentoRepository.save(medicaoInstrumento);

	}

}
