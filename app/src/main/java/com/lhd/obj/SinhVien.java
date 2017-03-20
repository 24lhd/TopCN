package com.lhd.obj;

import java.io.Serializable;

public class SinhVien implements Serializable {
	private String ma;
	private String ten;
	private String lop;
	private String k;
	private String tl;
	private String nam;
	private String nbatdau;
	private String nketthuc;
	private String mahe;
	private String manganh;
	private String khoa;

	public String getKhoa() {
		return khoa;
	}

	public void setKhoa(String khoa) {
		this.khoa = khoa;
	}

	public SinhVien(String ma, String ten, String lop, String k, String tl, String nam, String nbatdau, String nketthuc,String mahe, String manganh, String khoa) {
		super();
		this.ma = ma;
		this.ten = ten;
		this.lop = lop;
		this.k = k;
		this.tl = tl;
		this.nam = nam;
		this.nbatdau = nbatdau;
		this.nketthuc = nketthuc;
		this.mahe = mahe;
		this.manganh = manganh;
		this.khoa = khoa;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getLop() {
		return lop;
	}

	public void setLop(String lop) {
		this.lop = lop;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getTl() {
		return tl;
	}

	public void setTl(String tl) {
		this.tl = tl;
	}

	public String getNam() {
		return nam;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	public String getNbatdau() {
		return nbatdau;
	}

	public void setNbatdau(String nbatdau) {
		this.nbatdau = nbatdau;
	}

	public String getNketthuc() {
		return nketthuc;
	}

	public void setNketthuc(String nketthuc) {
		this.nketthuc = nketthuc;
	}

	public String getMahe() {
		return mahe;
	}

	public void setMahe(String mahe) {
		this.mahe = mahe;
	}

	public String getManganh() {
		return manganh;
	}

	public void setManganh(String manganh) {
		this.manganh = manganh;
	}

	@Override
	public String toString() {
		return "SinhVien [ma=" + ma + ", ten=" + ten + ", lop=" + lop + ", k=" + k + ", tl=" + tl + ", nam=" + nam
				+ ", nbatdau=" + nbatdau + ", nketthuc=" + nketthuc + ", mahe=" + mahe + ", manganh=" + manganh + "]";
	}

}
