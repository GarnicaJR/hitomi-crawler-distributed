package com.hitomi.crawler.api;

import java.time.Duration;
import java.time.Instant;

import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitomi.crawler.job.SampleJobService;

@RestController
public class JobController {

	@Autowired
	private JobScheduler jobScheduler;

}
