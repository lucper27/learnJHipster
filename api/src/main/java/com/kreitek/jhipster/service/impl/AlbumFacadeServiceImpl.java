package com.kreitek.jhipster.service.impl;

import com.kreitek.jhipster.service.*;
import com.kreitek.jhipster.service.criteria.AlbumCriteria;
import com.kreitek.jhipster.service.criteria.ArtistCriteria;
import com.kreitek.jhipster.service.dto.*;
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
        AlbumFacadeDTO result = new AlbumFacadeDTO();

        /*Verificar que el artista existe*/
        ArtistCriteria artistCriteria = new ArtistCriteria();
        StringFilter artistName = new StringFilter();
        artistName.setEquals(albumFacadeDTO.getArtist().getName());
        artistCriteria.setName(artistName);
        // el campo nombre es único en la bd, por lo que no pueden haber 2 artistas con el mismo nombre (al menos en esta bd ficticia)
        ArtistDTO artistDTO = albumFacadeDTO.getArtist();
        List<ArtistDTO> artistDTOS = artistQueryService.findByCriteria(artistCriteria);
        if (artistDTOS.size() > 0) {
            artistDTO = artistDTOS.get(0);
        } else {
            artistDTO = artistService.save(artistDTO);
        }

        /*Verificar si existe algún album de ese artista con ese nombre*/
        AlbumCriteria albumCriteria = new AlbumCriteria();
        LongFilter artistId = new LongFilter();
        artistId.setEquals(artistDTO.getId());
        StringFilter albumTitle = new StringFilter();
        albumTitle.setEquals(albumFacadeDTO.getTitle());
        albumCriteria.setTitle(albumTitle);
        albumCriteria.setArtistId(artistId);
        List<AlbumDTO> albumDTOS = albumQueryService.findByCriteria(albumCriteria);
            if (albumDTOS.isEmpty()) {
                // buscar estilo
                Optional<StyleDTO> styleOptional = styleService.findOneByName(albumFacadeDTO.getStyle().getName());
                StyleDTO styleDTO;
                if (styleOptional.isEmpty()) {
                    styleDTO = styleService.save(albumFacadeDTO.getStyle());
                } else {
                    styleDTO = styleOptional.get();
                }
                // crear albúm
                AlbumDTO albumDTO = new AlbumDTO();
                albumDTO.setArtist(artistDTO);
                albumDTO.setTitle(albumFacadeDTO.getTitle());
                albumDTO.setCover(albumFacadeDTO.getCover());
                albumDTO.setCoverContentType(albumFacadeDTO.getCoverContentType());
                albumDTO.setStyle(styleDTO);
                albumDTO = albumService.save(albumDTO);

                // crear canciones
                Set<SongDTO> songResult = new HashSet<>();
                AlbumDTO finalAlbumDTO = albumDTO;
                ArtistDTO finalArtistDTO = artistDTO;
                albumFacadeDTO.getSongs().forEach(eachSong -> {
                    eachSong.setArtist(finalArtistDTO);
                    eachSong.setAlbum(finalAlbumDTO);
                    songResult.add(songService.save(eachSong));
                });

                // construir el albumFacadeDTO
                result.setSongs(songResult);
                result.setArtist(albumDTO.getArtist());
                result.setTitle(albumDTO.getTitle());
                result.setStyle(albumDTO.getStyle());
                result.setCover(albumDTO.getCover());
                result.setCoverContentType(albumDTO.getCoverContentType());
            }
        return result;
    }

}
