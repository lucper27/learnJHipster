package com.kreitek.jhipster.service.impl;


import com.kreitek.jhipster.exception.DuplicatedAlbumException;
import com.kreitek.jhipster.service.*;
import com.kreitek.jhipster.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
@Component
public class AlbumFacadeServiceImpl implements AlbumFacadeService {

    private final Logger log = LoggerFactory.getLogger(AlbumFacadeServiceImpl.class);
    private final AlbumService albumService;

    private final ArtistService artistService;

    private final SongService songService;

    private final StyleService styleService;

    public AlbumFacadeServiceImpl(AlbumService albumService, ArtistService artistService, SongService songService, StyleService styleService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.styleService = styleService;
    }

    @Override
    @Transactional
    public AlbumFacadeDTO createAlbum(AlbumFacadeDTO albumFacadeDTO) {
        /*El albúm que se crerá supone que todas las canciones son del mismo artista*/
        AlbumFacadeDTO albumCreated = new AlbumFacadeDTO();
        boolean artistExists = artistService.verifyArtistExists(albumFacadeDTO);

        if (!artistExists) {
            albumFacadeDTO.setArtist(artistService.save(albumFacadeDTO.getArtist()));
        }

        boolean albumExists = albumService.albumExists(albumFacadeDTO);

        if (albumExists) {
            boolean styleExists = styleService.styleExists(albumFacadeDTO);
            if (!styleExists) {
                albumFacadeDTO.setStyle(styleService.save(albumFacadeDTO.getStyle()));
            }
            AlbumDTO newAlbumDTO = albumService.createAlbumFromFacade(albumFacadeDTO);
            Set<SongDTO> songResult = songService.addSongsToAlbum(albumFacadeDTO, newAlbumDTO);
            albumFacadeDTO.getSongs().clear();
            albumFacadeDTO.setSongs(songResult);
            albumCreated = albumFacadeDTO;
        } else {
            log.error("Album already exists");
        }
        return albumCreated;
    }

}
