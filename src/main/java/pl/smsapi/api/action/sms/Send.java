package pl.smsapi.api.action.sms;

import org.json.JSONObject;
import pl.smsapi.api.action.BaseAction;
import pl.smsapi.api.response.StatusResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

public class Send extends BaseAction<StatusResponse> {

	@Override
	public URI uri() throws URISyntaxException {

		String query = "";

		query += paramsLoginToQuery();
 		query += paramsBasicToQuery();
 		query += paramsOther();

		return new URI(proxy.getProtocol(), null, proxy.getHost(), proxy.getPort(), proxy.getPath()+"sms.do", query, null);
	}

	public Send() {
        this.params.put("encoding", "utf-8");
	}

	public Send setTo(String to) {
		this.to.add(to);
		return this;
	}

	public Send setTo(String[] to) {
		for (String item : to) {
			setTo(item);
		}
		return this;
	}

	public Send setGroup(String group) {
		this.group = group;
		return this;
	}

	public Send setDateSent(String date) {
		this.date = date;
		return this;
	}

	public Send setDateSent(long date) {
		Long time = date;
		return setDateSent(time.toString());
	}

	public Send setDateSent(Calendar cal) {
		long time = cal.getTimeInMillis() / 1000;
		return setDateSent(time);
	}

	public Send setIDx(String idx) {
		this.idx.add(idx);
		return this;
	}

	public Send setIDx(String[] idx) {
		for (String item : idx) {
			setIDx(item);
		}
		return this;
	}

	public Send setCheckIDx(boolean check) {
		if (check == true) {
			params.put("check_idx", "1");
		} else if (check == false) {
			params.put("check_idx", "0");
		}

		return this;
	}

	public Send setPartner(String partner) {
		params.put("partner_id", partner);
		return this;
	}

	public Send setText(String text) {
		params.put("message", text);
		return this;
	}

	public Send setDateExpire(String date) {
		params.put("expiration_date", date);
		return this;
	}

	public Send setDateExpire(long date) {
		Long time = date;
		return setDateExpire(time.toString());
	}

	public Send setDateExpire(Calendar cal) {
		long time = cal.getTimeInMillis() / 1000;
		return setDateExpire(time);
	}

	public Send setSender(String sender) {
		params.put("from", sender);
		return this;
	}

	public Send setSingle(boolean single) {
		if (single == true) {
			params.put("single", "1");
		} else if (single == false) {
			params.put("single", "0");
		}

		return this;
	}

	public Send setNoUnicode(boolean noUnicode) {
		if (noUnicode == true) {
			params.put("nounicode", "1");
		} else if (noUnicode == false) {
			params.put("nounicode", "0");
		}

		return this;
	}

	public Send setDataCoding(String dataCoding) {
		params.put("datacoding", dataCoding);
		return this;
	}

	public Send setFlash(boolean flash) {
		if (flash == true) {
			params.put("flash", "1");
		} else if (flash == false) {
			params.remove("flash");
		}

		return this;
	}
	
	public Send setFast(boolean fast) {
		if (fast == true) {
			params.put("fast", "1");
		} else if (fast == false) {
			params.remove("fast");
		}

		return this;
	}	

	public Send setNormalize(boolean normalize) {

		if (normalize == true) {
			params.put("normalize", "1");
		} else if (normalize == false) {
			params.remove("normalize");
		}

		return this;
	}

    public Send setParam(int i, String[] text) {
        return this.setParam(i, this.stringJoin(text, "|"));
    }

    protected String stringJoin(String[] list, String glue) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < list.length; i++ ) {

            stringBuilder.append(list[i]);
            if( i+1 < list.length ) {
                stringBuilder.append(glue);
            }
        }

        return stringBuilder.toString();
    }

    public Send setParam(int i, String text) {

        if( i < 0 || i > 3 ) {
            throw new ArrayIndexOutOfBoundsException();
        }

        params.put("param"+ new Integer(i+1).toString(), text);

        return this;
    }

    protected StatusResponse createResponse(String data) {
        JSONObject jsonObject = new JSONObject(data);
        return new StatusResponse(jsonObject.getInt("count"), jsonObject.optJSONArray("list"));
    }
}