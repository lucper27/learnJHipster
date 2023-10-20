package com.kreitek.jhipster.service.impl;


import com.kreitek.jhipster.service.*;
import com.kreitek.jhipster.service.criteria.AlbumCriteria;
import com.kreitek.jhipster.service.criteria.ArtistCriteria;
import com.kreitek.jhipster.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Component
public class AlbumFacadeServiceImpl implements AlbumFacadeService {

    private final Logger log = LoggerFactory.getLogger(AlbumFacadeServiceImpl.class);
    private final AlbumService albumService;

    private final AlbumQueryService albumQueryService;

    private final ArtistService artistService;

    private final ArtistQueryService artistQueryService;

    private final SongService songService;

    private final StyleService styleService;

    public AlbumFacadeServiceImpl(AlbumService albumService, AlbumQueryService albumQueryService, ArtistService artistService, ArtistQueryService artistQueryService, SongService songService, StyleService styleService) {
        this.albumService = albumService;
        this.albumQueryService = albumQueryService;
        this.artistService = artistService;
        this.artistQueryService = artistQueryService;
        this.songService = songService;
        this.styleService = styleService;
    }

    @Override
    @Transactional
    public AlbumFacadeDTO createAlbum(AlbumFacadeDTO albumFacadeDTO) {
        /*El albúm que se crerá supone que todas las canciones son del mismo artista*/


        /*Verificar que el artista existe*/
        verifyArtistOrCreateIfNotPresent(albumFacadeDTO);
//        ArtistCriteria artistCriteria = new ArtistCriteria();
//        StringFilter artistName = new StringFilter();
//        artistName.setEquals(albumFacadeDTO.getArtist().getName());
//        artistCriteria.setName(artistName);
//        // el campo nombre es único en la bd, por lo que no pueden haber 2 artistas con el mismo nombre (al menos en esta bd ficticia)
//        List<ArtistDTO> artistDTOS = artistQueryService.findByCriteria(artistCriteria);
//        if (artistDTOS.size() > 0) {
//            albumFacadeDTO.setArtist(artistDTOS.get(0));
//        } else {
//            albumFacadeDTO.setArtist(artistService.save(albumFacadeDTO.getArtist()));
//        }

        /*------*/
        /*Verificar si existe algún album de ese artista con ese nombre*/
        /*boolean albumExists = verifyAlbum(artistDTO, albumFacadeDTO);*/
        boolean albumExists = albumExists(albumFacadeDTO);
//        AlbumCriteria albumCriteria = new AlbumCriteria();
//        LongFilter artistId = new LongFilter();
//        artistId.setEquals(albumFacadeDTO.getArtist().getId());
//        StringFilter albumTitle = new StringFilter();
//        albumTitle.setEquals(albumFacadeDTO.getTitle());
//        albumCriteria.setTitle(albumTitle);
//        albumCriteria.setArtistId(artistId);
//        List<AlbumDTO> albumDTOS = albumQueryService.findByCriteria(albumCriteria);
        /*------*/

            if (albumExists) {
                /*------*/
                // buscar estilo
                findStyleOrCreateIfNotPresent(albumFacadeDTO);
//                Optional<StyleDTO> styleOptional = styleService.findOneByName(albumFacadeDTO.getStyle().getName());
//                if (styleOptional.isEmpty()) {
//                    albumFacadeDTO.setStyle(styleService.save(albumFacadeDTO.getStyle()));
//                } else {
//                    albumFacadeDTO.setStyle(styleOptional.get());
//                }
                /*------*/

                /*------*/
                // crear albúm
                AlbumDTO newAlbumDTO = createAlbumFromFacade(albumFacadeDTO);
//                AlbumDTO albumDTO = new AlbumDTO();
//                albumDTO.setArtist(albumFacadeDTO.getArtist());
//                albumDTO.setTitle(albumFacadeDTO.getTitle());
//                albumDTO.setCover(albumFacadeDTO.getCover());
//                albumDTO.setCoverContentType(albumFacadeDTO.getCoverContentType());
//                albumDTO.setStyle(albumFacadeDTO.getStyle());
//                albumDTO = albumService.save(albumDTO);
                /*------*/

                // crear canciones
                Set<SongDTO> songResult = addSongsToAlbum(albumFacadeDTO, newAlbumDTO);
//                Set<SongDTO> songResult = new HashSet<>();
//                AlbumDTO finalAlbumDTO = albumDTO;
//                ArtistDTO finalArtistDTO = albumFacadeDTO.getArtist();
//                albumFacadeDTO.getSongs().forEach(eachSong -> {
//                    eachSong.setArtist(finalArtistDTO);
//                    eachSong.setAlbum(finalAlbumDTO);
//                    songResult.add(songService.save(eachSong));
//                });
                albumFacadeDTO.getSongs().clear();
                albumFacadeDTO.setSongs(songResult);

            } else {
                log.error("Album already exists");
                //throw DuplicatedAlbumException
            }
        return albumFacadeDTO;
    }

    //crear canciones
    private Set<SongDTO> addSongsToAlbum(AlbumFacadeDTO albumFacadeDTO, AlbumDTO newAlbumDTO) {
        Set<SongDTO> songResult = new HashSet<>();
        AlbumDTO finalAlbumDTO = newAlbumDTO;
        ArtistDTO finalArtistDTO = albumFacadeDTO.getArtist();
        albumFacadeDTO.getSongs().forEach(eachSong -> {
            eachSong.setArtist(finalArtistDTO);
            eachSong.setAlbum(finalAlbumDTO);
            songResult.add(songService.save(eachSong));
        });

        return songResult;
    }

    //meterlo en StyleService
    private void findStyleOrCreateIfNotPresent(AlbumFacadeDTO albumFacadeDTO) {
        Optional<StyleDTO> styleOptional = styleService.findOneByName(albumFacadeDTO.getStyle().getName());
        if (styleOptional.isEmpty()) {
            albumFacadeDTO.setStyle(styleService.save(albumFacadeDTO.getStyle()));
        } else {
            albumFacadeDTO.setStyle(styleOptional.get());
        }
    }

    private boolean albumExists(AlbumFacadeDTO albumFacadeDTO) {
        AlbumCriteria albumCriteria = new AlbumCriteria();
        LongFilter artistId = new LongFilter();
        artistId.setEquals(albumFacadeDTO.getArtist().getId());
        StringFilter albumTitle = new StringFilter();
        albumTitle.setEquals(albumFacadeDTO.getTitle());
        albumCriteria.setTitle(albumTitle);
        albumCriteria.setArtistId(artistId);
        List<AlbumDTO> albumDTOS = albumQueryService.findByCriteria(albumCriteria);
        if (albumDTOS.isEmpty()) {
            return true;
        } else {
            log.error("Album already exists with id -> " + albumDTOS.get(0).getId());
            //throw DuplicatedAlbumException
            return false;
        }
    }

    private void verifyArtistOrCreateIfNotPresent(AlbumFacadeDTO albumFacadeDTO) {
        /*Verificar que el artista existe*/
        ArtistCriteria artistCriteria = new ArtistCriteria();
        StringFilter artistName = new StringFilter();
        artistName.setEquals(albumFacadeDTO.getArtist().getName());
        artistCriteria.setName(artistName);
        // el campo nombre es único en la bd, por lo que no pueden haber 2 artistas con el mismo nombre (al menos en esta bd ficticia)
        List<ArtistDTO> artistDTOS = artistQueryService.findByCriteria(artistCriteria);
        if (artistDTOS.size() > 0) {
            albumFacadeDTO.setArtist(artistDTOS.get(0));
        } else {
            albumFacadeDTO.setArtist(artistService.save(albumFacadeDTO.getArtist()));
        }
    }

    private AlbumDTO createAlbumFromFacade(AlbumFacadeDTO albumFacadeDTO) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setArtist(albumFacadeDTO.getArtist());
        albumDTO.setTitle(albumFacadeDTO.getTitle());
        albumDTO.setCover(albumFacadeDTO.getCover());
        albumDTO.setCoverContentType(albumFacadeDTO.getCoverContentType());
        albumDTO.setStyle(albumFacadeDTO.getStyle());
        return albumService.save(albumDTO);
    }

}
