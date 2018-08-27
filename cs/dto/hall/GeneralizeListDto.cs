using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GeneralizeListDto : SerializeMessage {
	[ProtoMember(1)]
	public long allAward;
	[ProtoMember(2)]
	public string generalizeUserName;
	[ProtoMember(3)]
	public List<GeneralizeDto> generalizeDtoList;
}
