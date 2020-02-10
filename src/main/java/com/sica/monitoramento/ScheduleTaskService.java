package com.sica.monitoramento;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import com.sica.service.dto.InstrumentoMonitoramentoDTO;

@Service
@RabbitListener(queues = "inclusaoInstrumentoMonitoramentoQueue")
public class ScheduleTaskService {

	// Task Scheduler
	TaskScheduler scheduler;

	// A map for keeping scheduled tasks
	Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

	public ScheduleTaskService(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void addTaskToScheduler(InstrumentoMonitoramentoDTO instrumentoMonitoramento) {
		LeitorInstrumentosMonitoramentoService task = get();
		task.setApi(instrumentoMonitoramento.getUrlGetMedicao());
		task.setIdentificador(instrumentoMonitoramento.getIdentificao());
		ScheduledFuture<?> scheduledTask = scheduler.schedule(task,
				new PeriodicTrigger(instrumentoMonitoramento.getIntervaloMedicaoAPI().longValue(), TimeUnit.SECONDS));
		jobsMap.put(instrumentoMonitoramento.getIdentificao(), scheduledTask);
	}

	@Lookup
	public LeitorInstrumentosMonitoramentoService get() {
		return null;
	}

	@RabbitHandler
	public void incluirInstrumentoMonitoramentoQueue(InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO) {
		addTaskToScheduler(instrumentoMonitoramentoDTO);
	}

	// Remove scheduled task
	public void removeTaskFromScheduler(String id) {
		ScheduledFuture<?> scheduledTask = jobsMap.get(id);
		if (scheduledTask != null) {
			scheduledTask.cancel(true);
			jobsMap.put(id, null);
		}
	}

	// A context refresh event listener
	@EventListener({ ContextRefreshedEvent.class })
	void contextRefreshedEvent() {
		// Get all tasks from DB and reschedule them in case of context restarted
	}
}