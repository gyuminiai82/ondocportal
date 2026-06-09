package kr.ondoc.scheduler;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import kr.ondoc.domain.sybase.crm.CrmRevptNaverVO;
import kr.ondoc.mapper.sybase.crm.CrmRevptNaverMapper;

public class OnlineScheduleRestClient {
	
	private CrmRevptNaverMapper revptNaverMapper;
	
	public OnlineScheduleRestClient(CrmRevptNaverMapper revptNaverMapper) {
		// TODO Auto-generated constructor stub
		this.revptNaverMapper = revptNaverMapper;
	}

	public void pollingDocTalk(String emrAccessKey, String hosnum) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://emr-p.doctalk.co.kr/apis/emr/v1/polling?accessKey="+emrAccessKey+"&hospitalId="+hosnum;
		
		HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> bodyForm = new LinkedMultiValueMap<>();
		
        HttpEntity httpEntity = new HttpEntity<>(bodyForm, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity(url, httpEntity, Map.class);

		JSONObject jObject = new JSONObject(response.getBody());
		JSONObject header = jObject.getJSONObject("header");
		String headerCode = header.getString("code");
		JSONObject body = jObject.getJSONObject("body");
		
		if(headerCode.equals("0000")) {
			JSONArray dataList = body.getJSONArray("dataList");
			dataList.forEach(data -> {
				JSONObject obj = (JSONObject) data;
				String path = obj.getString("path");
				String reg_date = obj.getString("regDate");
				
				JSONObject parameters = obj.getJSONObject("parameters");
				if(path.equals("add-booking")) {
					String customFormData = parameters.getString("customFormData");
					String period = Integer.toString(parameters.getInt("period"));
					String preBookingKey = parameters.isNull("preBookingKey") ? "" : parameters.getString("preBookingKey");
					String naverUserId = parameters.getString("naverUserId");
					String platformType = parameters.getString("platformType");
					String reservationName = parameters.getString("reservationName");
					String reservationKey = parameters.getString("reservationKey");
					String datetime = parameters.getString("datetime");
					String visitorName = parameters.isNull("visitorName") ? "" : parameters.getString("visitorName");
					String userRequest = parameters.isNull("userRequest") ? "" : parameters.getString("userRequest");
					String hospitalId = parameters.getString("hospitalId");
					String phone = parameters.getString("phone");
					String bookingType = parameters.getString("bookingType");
					String bookingKey = parameters.getString("bookingKey");
					String name = parameters.getString("name");
					String itemOptions = parameters.getString("itemOptions");
					String state = parameters.getString("state");
					String visitorPhone = parameters.isNull("visitorPhone") ? "" : parameters.getString("visitorPhone");
					String email = parameters.getString("email");
					
					StringBuilder str = new StringBuilder();
					
					str.append("customFormData:"); str.append(customFormData + "\r\n");
					str.append("period:"); str.append(period + "\r\n");
					str.append("preBookingKey:"); str.append(preBookingKey + "\r\n");
					str.append("naverUserId:"); str.append(naverUserId + "\r\n");
					str.append("platformType:"); str.append(platformType + "\r\n");
					str.append("reservationName:"); str.append(reservationName + "\r\n");
					str.append("reservationKey:"); str.append(reservationKey + "\r\n");
					str.append("datetime:"); str.append(datetime + "\r\n");
					str.append("visitorName:"); str.append(visitorName + "\r\n");
					str.append("userRequest:"); str.append(userRequest + "\r\n");
					str.append("hospitalId:"); str.append(hospitalId + "\r\n");
					str.append("phone:"); str.append(phone + "\r\n");
					str.append("bookingType:"); str.append(bookingType + "\r\n");
					str.append("bookingKey:"); str.append(bookingKey + "\r\n");
					str.append("name:"); str.append(name + "\r\n");
					str.append("itemOptions:"); str.append(itemOptions + "\r\n");
					str.append("state:"); str.append(state + "\r\n");
					str.append("visitorPhone:"); str.append(visitorPhone + "\r\n");
					str.append("email:"); str.append(email + "\r\n");
					
					System.out.println(str.toString());
					
					CrmRevptNaverVO vo = new CrmRevptNaverVO();
					vo.setRev_customformdata(customFormData); //예약커스텀 정보
					vo.setRev_period(period);
					vo.setRev_prebookingkey(preBookingKey);
					vo.setRev_naveruserid(naverUserId);
					vo.setRev_platformtype(platformType);
					vo.setRev_reservationname(reservationName);
					vo.setRev_reservationkey(reservationKey);
					vo.setRev_datetime(datetime);
					vo.setRev_visitorname(visitorName);
					vo.setRev_userrequest(userRequest);
					//vo.setRev_hospitalId(hospitalId);	//필드 없어서 제외
					vo.setRev_bookingkey(bookingKey);
					vo.setRev_name(name);
					vo.setRev_phone(phone);
					vo.setRev_state(state);
					vo.setRev_bookingtype(bookingType);
					vo.setRev_email(email);
					vo.setRev_userrequest(userRequest);
					vo.setRev_visitorphone(visitorPhone);
					vo.setRev_itemoptions(itemOptions);	//시술정보
					
					String[] arrDatetime = datetime.replaceAll("-", "").replaceAll(":", "").split(" ");
					
					vo.setRev_date(arrDatetime[0]);
					vo.setRev_time(arrDatetime[1].substring(0, 4));
					
					vo.setRev_confirm("N"); /*N:신규발생건, T:예약중 Y:완료, S:네이버에서 예약 수정건, D:취소*/
					
					String ptnoName = (!visitorName.equals("")) ? visitorName : name;
					String ptnoPhone = (!visitorPhone.equals("")) ? visitorPhone : phone;
					
					String ptno = "";
					try {
						ptno = revptNaverMapper.selectPtno(ptnoName, ptnoPhone.substring(3, 7), ptnoPhone.substring(7, 11));
						if(ptno != null) vo.setRev_ptno(ptno);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getLocalizedMessage());
					}
					
					try {
						revptNaverMapper.insertRevptNaver(vo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				} else if(path.equals("update-booking-state")) {
					String datetime = parameters.getString("datetime");
					String cancelledDesc = parameters.isNull("cancelledDesc") ? "" : parameters.getString("cancelledDesc");
					String hospitalId = parameters.getString("hospitalId");
					String bookingKey = parameters.getString("bookingKey");
					String platformType = parameters.getString("platformType");
					String state = parameters.getString("state");
					String reservationKey = parameters.getString("reservationKey");
					
					StringBuilder str = new StringBuilder();
					
					str.append("datetime:"); str.append(datetime + "\r\n");
					str.append("cancelledDesc:"); str.append(cancelledDesc + "\r\n");
					str.append("hospitalId:"); str.append(hospitalId + "\r\n");
					str.append("bookingKey:"); str.append(bookingKey + "\r\n");
					str.append("platformType:"); str.append(platformType + "\r\n");
					str.append("state:"); str.append(state + "\r\n");
					str.append("reservationKey:"); str.append(reservationKey);
					
					CrmRevptNaverVO vo = new CrmRevptNaverVO();
					vo.setRev_datetime(datetime);
					vo.setRev_cancelleddesc(cancelledDesc);
					//vo.setRev_hospitalId(hospitalId);
					vo.setRev_bookingkey(bookingKey);
					vo.setRev_platformtype(platformType);
					vo.setRev_state(state);
					vo.setRev_reservationkey(reservationKey);
					
					//취소 요청시 N으로 해야 알림뜸
					if(state.equals("BS_CANCELLED")) vo.setRev_confirm("N");
					
					try {
						CrmRevptNaverVO revptNaverVO = revptNaverMapper.selectRevptNaver(vo);
						if(revptNaverVO != null) {
							if(revptNaverVO.getRev_confirm().equals("N")) vo.setRev_confirm("Y");
							
							revptNaverMapper.updateRevptNaver(vo);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
		}
	}
}

