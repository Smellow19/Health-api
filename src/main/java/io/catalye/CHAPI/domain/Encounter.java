package io.catalye.CHAPI.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="encounters")
public class Encounter {

	@Id
	String _id;
	String patientid;
	String notes;
	String vistcode;
	String provider;
	String billingcode;
	Integer totalcost;
	Integer copay;
	String chiefcomplaint;
	Integer pulse;
	Integer systolic;
	Integer diastolic;
	String date;
	public String get_Id() {
		return _id;
	}
	public String getPatientid() {
		return patientid;
	}
	public String getNotes() {
		return notes;
	}
	public String getVistcode() {
		return vistcode;
	}
	public String getProvider() {
		return provider;
	}
	public String getBillingcode() {
		return billingcode;
	}
	public Integer getTotalcost() {
		return totalcost;
	}
	public Integer getCopay() {
		return copay;
	}
	public String getChiefcomplaint() {
		return chiefcomplaint;
	}
	public Integer getPulse() {
		return pulse;
	}
	public Integer getSystolic() {
		return systolic;
	}
	public Integer getDiastolic() {
		return diastolic;
	}
	public String getDate() {
		return date;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setVistcode(String vistcode) {
		this.vistcode = vistcode;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public void setBillingcode(String billingcode) {
		this.billingcode = billingcode;
	}
	public void setTotalcost(Integer totalcost) {
		this.totalcost = totalcost;
	}
	public void setCopay(Integer copay) {
		this.copay = copay;
	}
	public void setChiefcomplaint(String chiefcomplaint) {
		this.chiefcomplaint = chiefcomplaint;
	}
	public void setPulse(Integer pulse) {
		this.pulse = pulse;
	}
	public void setSystolic(Integer systolic) {
		this.systolic = systolic;
	}
	public void setDiastolic(Integer diastolic) {
		this.diastolic = diastolic;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
