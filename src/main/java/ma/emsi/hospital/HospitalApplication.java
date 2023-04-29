package ma.emsi.hospital;

import ma.emsi.hospital.entities.*;
import ma.emsi.hospital.repositories.ConsultationRepository;
import ma.emsi.hospital.repositories.MedecinRepository;
import ma.emsi.hospital.repositories.PatientRepository;
import ma.emsi.hospital.repositories.RendezVousRepository;
import ma.emsi.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}
	@Bean
	CommandLineRunner start(IHospitalService iHospitalService ,
							PatientRepository patientRepository,
							RendezVousRepository rendezVousRepository,
						MedecinRepository medecinRepository){
return args -> {
	Stream.of("Hassan","Mohammed","Najat")
			.forEach(name->{
				Patient patient=new Patient();
				patient.setNom(name);
				patient.setDateNaissance(new Date());
				patient.setMalade(false);
				iHospitalService.savePatient(patient);
			});

	Stream.of("ayman","said","hayat")
			.forEach(name->{
				Medecin medecin=new Medecin();
				medecin.setNom(name);
				medecin.setSpecialite(Math.random()>0.5?"cardio":"Dentiste");
				medecin.setEmail(name+"@gmail.com");
				iHospitalService.saveMedecin(medecin);
			});

	Patient patient=patientRepository.findById(1L).orElse(null);
	Patient patient1=patientRepository.findByNom("Mohammed");
	Medecin medecin=medecinRepository.findByNom("hayat");
	RendezVous rendezVous=new RendezVous();
	rendezVous.setDate(new Date());
	rendezVous.setStatuts(StatusRDV.PENDING);
	rendezVous.setMedecin(medecin);
	rendezVous.setPatient(patient);
	RendezVous saveDRDV = iHospitalService.saveRDV(rendezVous);
	System.out.println(saveDRDV.getId());
RendezVous rendezVous1=rendezVousRepository.findAll().get(0);
  	Consultation consultation=new Consultation();
	  consultation.setDateConsultation(new Date());
	  consultation.setRendezVous(rendezVous1);
	  consultation.setRapport("Rapport de la consultation.........");
	  iHospitalService.saveConsultation(consultation);



};
	}

}
