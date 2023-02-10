package com.advancelatam.data.entities.core;

import java.io.Serializable;
import java.util.Date;

public interface Dated extends Serializable {
	Date getFechaCrea();

	Date getFechaModifica();

	void setFechaCrea(Date fechaCrea);

	void setFechaModifica(Date fechaModifica);
}
