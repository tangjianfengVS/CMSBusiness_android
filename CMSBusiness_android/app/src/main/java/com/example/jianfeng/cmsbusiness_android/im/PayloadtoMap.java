package com.example.jianfeng.cmsbusiness_android.im;

import com.pythonsh.common.ApplicationException;
import com.pythonsh.common.RawPacket;
import com.pythonsh.common.adaptor.base.AdaptorContext;
import com.pythonsh.common.adaptor.base.SecuredDuplexAdaptor;

/**
 * Created by duantao
 *
 * @date on 2017/12/11 10:11
 */
public class PayloadtoMap extends SecuredDuplexAdaptor {

	public PayloadtoMap(RawPacket rawPacket) {
		super(rawPacket);
	}

	@Override
	public RawPacket writeToRaw(AdaptorContext adaptorContext) {
		return null;
	}

	@Override
	public short getObserverId() {
		return 0;
	}

	@Override
	public void read(AdaptorContext adaptorContext) throws ApplicationException {

	}

	@Override
	public void preWrite(AdaptorContext adaptorContext) throws ApplicationException {

	}

}