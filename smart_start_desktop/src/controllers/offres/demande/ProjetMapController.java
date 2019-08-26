package controllers.offres.demande;

import java.net.URL;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import entities.offres.Projet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import services.offres.localeProjet.LocaleProjetService;

public class ProjetMapController implements Initializable, MapComponentInitializedListener {

	@FXML
	private GoogleMapView mapView;

	private GoogleMap map;
	
	private Projet projet= new Projet();
	
	private LocaleProjetService localeProjetService = new LocaleProjetService();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		mapView.addMapInializedListener(this);
	}    


	@Override
	public void mapInitialized() {
		Double x=localeProjetService.getPositionX(projet);
		Double y=localeProjetService.getPositionY(projet);
		LatLong locale = new LatLong(x, y);
		

		//Set the initial properties of the map.
		MapOptions mapOptions = new MapOptions();

		mapOptions.center(new LatLong(47.6097, -122.3331))
		.mapType(MapTypeIdEnum.SATELLITE)
		.overviewMapControl(true)
		.panControl(true)
		.rotateControl(true)
		.scaleControl(true)
		.streetViewControl(true)
		.zoomControl(true)
		.zoom(15);

		map = mapView.createMap(mapOptions);

		//Add markers to the map
		MarkerOptions markerOptions1 = new MarkerOptions();
		markerOptions1.position(locale);

		Marker joeSmithMarker = new Marker(markerOptions1);

		map.addMarker( joeSmithMarker );


		InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
		infoWindowOptions.content("<h2>"+projet.getNomProjet()+"</h2>"
				+ "<h4>"+projet.getTitreProjet()
				+ "  </h4> "+projet.getDescription());


        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
        fredWilkeInfoWindow.open(map, joeSmithMarker);
	
	}   
	
	
	public void initProjet(Projet projet) {
		this.projet = projet;
	}
}




