/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

#import <Foundation/Foundation.h>
#import "BaseService.h"

/**
 * author Bruno Farache
 */
@interface ShoppingItemService_v62 : BaseService

- (void)addBookItemsWithGroupId:(long)groupId categoryId:(long)categoryId isbns:(NSArray *)isbns error:(NSError **)error;
- (NSDictionary *)addItemWithGroupId:(long)groupId categoryId:(long)categoryId sku:(NSString *)sku name:(NSString *)name description:(NSString *)description properties:(NSString *)properties fieldsQuantities:(NSString *)fieldsQuantities requiresShipping:(BOOL)requiresShipping stockQuantity:(int)stockQuantity featured:(BOOL)featured sale:(NSDictionary *)sale smallImage:(BOOL)smallImage smallImageURL:(NSString *)smallImageURL smallFile:(NSDictionary *)smallFile mediumImage:(BOOL)mediumImage mediumImageURL:(NSString *)mediumImageURL mediumFile:(NSDictionary *)mediumFile largeImage:(BOOL)largeImage largeImageURL:(NSString *)largeImageURL largeFile:(NSDictionary *)largeFile itemFields:(NSArray *)itemFields itemPrices:(NSArray *)itemPrices serviceContext:(NSDictionary *)serviceContext error:(NSError **)error;
- (void)deleteItemWithItemId:(long)itemId error:(NSError **)error;
- (int)getCategoriesItemsCountWithGroupId:(long)groupId categoryIds:(NSArray *)categoryIds error:(NSError **)error;
- (NSDictionary *)getItemWithItemId:(long)itemId error:(NSError **)error;
- (NSArray *)getItemsWithGroupId:(long)groupId categoryId:(long)categoryId error:(NSError **)error;
- (NSArray *)getItemsWithGroupId:(long)groupId categoryId:(long)categoryId start:(int)start end:(int)end obc:(NSDictionary *)obc error:(NSError **)error;
- (int)getItemsCountWithGroupId:(long)groupId categoryId:(long)categoryId error:(NSError **)error;
- (NSArray *)getItemsPrevAndNextWithItemId:(long)itemId obc:(NSDictionary *)obc error:(NSError **)error;
- (NSDictionary *)updateItemWithItemId:(long)itemId groupId:(long)groupId categoryId:(long)categoryId sku:(NSString *)sku name:(NSString *)name description:(NSString *)description properties:(NSString *)properties fieldsQuantities:(NSString *)fieldsQuantities requiresShipping:(BOOL)requiresShipping stockQuantity:(int)stockQuantity featured:(BOOL)featured sale:(NSDictionary *)sale smallImage:(BOOL)smallImage smallImageURL:(NSString *)smallImageURL smallFile:(NSDictionary *)smallFile mediumImage:(BOOL)mediumImage mediumImageURL:(NSString *)mediumImageURL mediumFile:(NSDictionary *)mediumFile largeImage:(BOOL)largeImage largeImageURL:(NSString *)largeImageURL largeFile:(NSDictionary *)largeFile itemFields:(NSArray *)itemFields itemPrices:(NSArray *)itemPrices serviceContext:(NSDictionary *)serviceContext error:(NSError **)error;

@end