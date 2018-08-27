using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game2CardListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<Game2CardDto> cardDtoList;
}
