package com.example.yuanMatch.models.request;

import lombok.Data;

import java.io.Serializable;

/*
 *@author  fyy
 *@version 1.0
 *@time    2024-03-26 12:41
 *@project YuanMatch
 */
@Data
public class DeleteRequest implements Serializable
{

    private static final long serialVersionUID = -5860707094194210842L;

    private long id;
}
