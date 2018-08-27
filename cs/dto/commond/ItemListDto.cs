using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class ItemListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<ItemDto> items;
}
