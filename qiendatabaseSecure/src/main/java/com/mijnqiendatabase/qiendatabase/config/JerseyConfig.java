package com.mijnqiendatabase.qiendatabase.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.mijnqiendatabase.qiendatabase.api.*;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {

		register(AdminApi.class); //--> commando voor aanroepen Api
		register(DagApi.class); //--> commando voor aanroepen Api
		register(KlantApi.class); //--> commando voor aanroepen Api
		register(KostensoortApi.class); //--> commando voor aanroepen Api
		register(UserApi.class); //--> commando voor aanroepen Api
		register(TraineeApi.class); //--> commando voor aanroepen Api
		register(UurApi.class); //--> commando voor aanroepen Api
		register(UursoortApi.class); //--> commando voor aanroepen Api
		register(KostenApi.class); //--> commando voor aanroepen Api
	}
}
