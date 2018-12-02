package com.webbuilders.webgym.services;


import com.webbuilders.webgym.commands.DetailsCommand;

public interface DetailService {

    DetailsCommand findByProductIdAndDetailId(Long productId, Long detailId);

    DetailsCommand saveDetailCommand(DetailsCommand command);
}
